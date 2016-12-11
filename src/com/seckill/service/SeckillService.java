package com.seckill.service;

import java.util.List;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

/**
 * 业务接口：站在"使用者"角度设计接口
 * 三个方面：方法定义粒度,参数,返回类型(return 类型(友好)/异常)
 * @author xiaobai
 * @date 2016年11月6日下午5:50:36
 */
public interface SeckillService {
	
	/**
	 * 查询所有秒杀记录
	 * @author xiaobai
	 * @date 2016年11月6日下午5:55:12
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 根据id查询秒杀记录
	 * @author xiaobai
	 * @date 2016年11月6日下午6:23:52
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出接口地址
	 * 否则输出系统时间和秒杀时间
	 * @author xiaobai
	 * @date 2016年11月6日下午5:55:48
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @author xiaobai
	 * @date 2016年11月6日下午6:15:44
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @throws SeckillCloseException	秒杀关闭异常
	 * @throws RepeatKillException		重复秒杀异常
	 * @throws SeckillException			秒杀异常
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) 
			throws SeckillCloseException,RepeatKillException,SeckillException;
	
	/**
	 * 执行秒杀操作 by 存储过程
	 * @author xiaobai
	 * @date 2016年12月11日下午10:26:18
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillCloseException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckillProceduce(long seckillId,long userPhone ,String md5);
}
