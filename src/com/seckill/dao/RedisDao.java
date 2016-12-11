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

	/**
	 * 从Redis中获取seckill
	 * @author xiaobai
	 * @date 2016年12月10日下午11:11:41
	 * @param seckillId
	 * @return
	 */
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
					/*//实现Serializable接口，序列化反序列化
					@SuppressWarnings("resource")
					ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("a.txt"));
					Object object = inputStream.readObject();
					if(object instanceof Seckill){
						Seckill seckill = (Seckill) object;
						return seckill;
					}*/
					
					// 获得一个空对象
					//seckill反序列化
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
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

	/**
	 * 将seckill存入Redis缓存中
	 * @author xiaobai
	 * @param <T>
	 * @date 2016年12月10日下午11:12:08
	 * @param seckill
	 * @return
	 */
	public <T> String putSeckill(Seckill seckill) {
		
		// set Object(Seckill) -> 序列化 -> byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil
						.toByteArray(seckill, schema, LinkedBuffer
								.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				/*//实现Serializable接口，序列化反序列化
				@SuppressWarnings("resource")
				ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("a.txt"));
				seckill.setNum(33);
				stream.writeObject(seckill);
				byte[] bytes = stream.toString().getBytes();*/
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