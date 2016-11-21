package com.seckill.exception;

/**
 * @author xiaobai
 * @date 2016年11月6日下午6:11:11
 */
@SuppressWarnings("serial")
public class SeckillCloseException extends SeckillException{

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}
}