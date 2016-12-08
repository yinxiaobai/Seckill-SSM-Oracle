package test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.RedisDao;
import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;

/**
 * @author xiaobai
 * @date 2016年12月9日上午12:35:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class RedisDaoTest {
	
	private long seckillId = 3;
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private SeckillDao seckillDao;
	
	/**
	 * Test method for {@link com.seckill.dao.RedisDao#getSeckill(java.lang.Long)}.
	 */
	@Test
	public void testSeckill() {
		//get and put
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null){
			seckill = seckillDao.queryById(seckillId);
			if(seckill != null){
				String result = redisDao.putSeckill(seckill);
				System.out.println(result);
				seckill = redisDao.getSeckill(seckillId);
				System.out.println(seckill);
			}
		}
	}
}