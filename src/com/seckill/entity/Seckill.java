package com.seckill.entity;

import java.util.Date;

/**
 * @author xiaobai
 * @date 2016年11月5日下午6:21:24
 */
public class Seckill/* implements Serializable*/{
	
	/**
	 * 通过实现Serializable接口，必须增加显式版本号
	 * 否则若反序列化之前改变了类，jvm将回重新生成另一个隐式版本号
	 * 2016年12月10日下午9:29:25
	 */
//	private static final long serialVersionUID = 1L;

	private long seckillId;
	
	private String name;
	
	private int num;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date createTime;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SecKill [seckillId=" + seckillId + ", name=" + name + ", num="
				+ num + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createTime=" + createTime + "]";
	}
}