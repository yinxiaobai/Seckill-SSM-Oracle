package com.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dao.cache.RedisDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStatEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;

/**
 * @author xiaobai
 * @date 2016年11月6日下午6:16:52
 */
// @Component 通用@
@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 注入service依赖
	// @Resource
	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	// md5盐值字符串,用来混淆md5
	private final String slat = "asdasdSae34@#%fd45#$fwdd21323sd";

	/**
	 * @date 2016年11月6日下午6:27:29
	 */
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(1, 5);
	}

	/**
	 * @date 2016年11月6日下午6:27:29
	 */
	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/**
	 * 秒杀开启时输出接口地址 否则输出系统时间和秒杀时间
	 * 
	 * @author xiaobai
	 * @date 2016年11月6日下午6:27:29
	 * @param seckillId
	 */
	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// 优化点：缓存优化：超时的基础上维护一致性
		/*
		 * get from cache if null get db else put cache locgoin
		 */

		// 1、访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);

		if (seckill == null) {
			// 2、访问数据库
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				// 3、放入redis
				redisDao.putSeckill(seckill);
			}
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 当前系统时间
		Date nowTime = new Date();

		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		// 转化特定字符串过程，不可逆
		String md5 = getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * md5加密
	 * 
	 * @author xiaobai
	 * @date 2016年11月6日下午6:43:30
	 * @param seckillId
	 * @return
	 */
	private String getMd5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * 执行秒杀操作
	 * 
	 * @author xiaobai
	 * @date 2016年11月6日下午6:27:29
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	@Override
	@Transactional
	/*
	 * 使用注解控制事务方法的优点： 1：开发团队达成一致约定，明确标明事务方法的编程风格
	 * 2：保证事务方法的执行时间尽可能短,不要穿插其他的网络操作,RPC/HTTP请求或者剥离到事务方法外部
	 * 3:不是所有的方法都需要事务,如只有一条修改操作、只读操作不需要事务控制
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillCloseException, RepeatKillException, SeckillException {
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("###########seckill data rewrite###########");
		}
		// 执行秒杀逻辑：减库存 + 购买行为
		Date nowTime = new Date();

		try {
			// 记录购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			// 唯一seckillId,userPhone
			if (insertCount <= 0) {
				// 重复秒杀
				throw new RepeatKillException("###########seckill repeated###########");
			} else {
				// 减库存 热点商品竞争
				// 减少update行级锁时间
				int updateCount = seckillDao.reduceNum(seckillId, nowTime);
				if (updateCount <= 0) {
					// 没有更新到记录，秒杀结束
					throw new SeckillCloseException("###########seckill is closed###########");
				} else {
					// 秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			log.error(e1.getMessage(), e1);
			throw e1;
		} catch (RepeatKillException e2) {
			log.error(e2.getMessage(), e2);
			throw e2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// 所有编译期异常，转化为运行期异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	/**
	 * @date 2016年12月11日下午10:27:07
	 */
	@Override
	public SeckillExecution executeSeckillProceduce(long seckillId, long userPhone, String md5) {
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		// 执行存储过程，result被赋值
		try {
			seckillDao.killByProcedure(map);
			// 获取result
			int result = MapUtils.getInteger(map, "result", -2);
			if (result == 1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}
}