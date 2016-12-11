package com.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.Seckill;

/**
 * DAO层 数据访问层
 * @author xiaobai
 * @date 2016年11月5日下午6:38:51
 */
public interface SeckillDao {
	/**
	 * 减库存
	 * @author xiaobai
	 * @date 2016年11月5日下午6:40:05
	 * @param seckillId
	 * @param killTime
	 * @return 如果影响行数>1，表示更新的记录行数
	 */
	//传入多个参数时需加上@Param注解
	int reduceNum(@Param("seckillId")long seckillId,@Param("killTime") Date killTime);
	
	/**
	 * 根据ID查询秒杀对象
	 * @author xiaobai
	 * @date 2016年11月5日下午6:40:56
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 分页查询秒杀列表
	 * @author xiaobai
	 * @date 2016年11月5日下午6:42:50
	 * @param start	查询起始条数
	 * @param end	查询终止条数
	 * @return
	 */
	List<Seckill> queryAll(@Param("start") int start,@Param("end")int end);
	
	/**
	 * 使用存储过程执行秒杀
	 * @author xiaobai
	 * @date 2016年12月11日下午10:36:36
	 * @param paramMap
	 */
	void killByProcedure (Map<String,Object> paramMap);
}