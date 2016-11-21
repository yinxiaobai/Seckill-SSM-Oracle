package com.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 * @author xiaobai
 * @date 2016年11月6日下午6:07:19
 */
@SuppressWarnings("serial")
public class RepeatKillException  extends SeckillException{

	public RepeatKillException(String message) {
		super(message);
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}
}
