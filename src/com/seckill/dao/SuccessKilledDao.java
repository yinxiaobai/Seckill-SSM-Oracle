package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.entity.SuccessKilled;

/**
 * DAO层 数据访问层
 * @author xiaobai
 * @date 2016年11月5日下午6:43:49
 */
public interface SuccessKilledDao {
	
	/**
	 * 插入购买明细，可过滤重复
	 * @author xiaobai
	 * @date 2016年11月5日下午6:44:30
	 * @param seckillId
	 * @param userPhone
	 * @return 插入的行数
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * 根据ID查询SuccessKilled并携带秒杀产品对象实体
	 * @author xiaobai
	 * @date 2016年11月5日下午6:45:37
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}