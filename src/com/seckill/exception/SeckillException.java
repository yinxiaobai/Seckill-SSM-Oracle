package com.seckill.exception;

/**
 * 秒杀相关异常
 * @author xiaobai
 * @date 2016年11月6日下午6:12:20
 */
@SuppressWarnings("serial")
public class SeckillException extends RuntimeException{

	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}
}
