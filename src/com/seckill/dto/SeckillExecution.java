package com.seckill.dto;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStatEnum;

/**
 * 封装秒杀执行后结果
 * @author xiaobai
 * @date 2016年11月6日下午6:01:43
 */
public class SeckillExecution {
	
	//id
	private long seckillId;
	
	//秒杀执行状态
	private int state;
	
	//状态表示
	private String stateInfo;
	
	//秒杀成功对象
	private SuccessKilled successKilled;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state
				+ ", stateInfo=" + stateInfo + ", successKilled="
				+ successKilled + "]";
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	public SeckillExecution(long seckillId, SeckillStatEnum state,
			SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
		this.successKilled = successKilled;
	}

	public SeckillExecution(long seckillId, SeckillStatEnum state) {
		super();
		this.seckillId = seckillId;
		this.state = state.getState();
		this.stateInfo = state.getStateInfo();
	}
}
