package com.seckill.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;

/**
 * @author xiaobai
 * @date 2016年12月9日上午12:18:55
 */
public class RedisDao {

	private static final Logger log = LoggerFactory.getLogger(RedisDao.class);

	private JedisPool jedisPool;

	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	private RuntimeSchema<Seckill> schema = RuntimeSchema
			.createFrom(Seckill.class);

	public Seckill getSeckill(long seckillId) {

		// redis操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();

			try {
				String key = "seckill:" + seckillId;
				// 并没有实现内部序列化操作
				// get -> byte[] -> 反序列化 -> Object(Seckill)
				// 采用自定义序列化
				// protostuff : pojo
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					// 获得一个空对象
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					// seckill反序列化
					return seckill;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> 序列化 -> byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil
						.toByteArray(seckill, schema, LinkedBuffer
								.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				// 超时缓存
				int timeout = 60 * 60;
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} catch (Exception e) {
				jedis.close();
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}
}
