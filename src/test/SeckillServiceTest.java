package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;

/**
 * @author xiaobai
 * @date 2016年11月6日下午9:43:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	/**
	 * Test method for {@link com.seckill.service.SeckillService#getSeckillList()}.
	 */
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		log.info("list={}",list);
	}

	/**
	 * Test method for {@link com.seckill.service.SeckillService#getById(long)}.
	 */
	@Test
	public void testGetById() {
		long id = 1;
		Seckill seckill = seckillService.getById(id);
		log.info("seckill={}",seckill);
	}

	/**
	 * Test method for {@link com.seckill.service.SeckillService#exportSeckillUrl(long)}.
	 */
	//集成测试代码完整逻辑，注意可重复执行
	@Test
	public void testSeckillLogic() {
		int id = 3;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			log.info("exporser={}",exposer);
			long seckillId = exposer.getSeckillId();
			long userPhone = 1323041232L;
			String md5 = exposer.getMd5();
			try{
				SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
				log.info("execution={}",execution);
			}catch(RepeatKillException e){
				log.error(e.getMessage());
			}catch(SeckillException e){
				log.error(e.getMessage());
			}
		}else{
			//秒杀未开启
			log.warn("exporser={}",exposer);
		}
		//Exposer [
		//exposed=true, 
		//md5=1a3e4f425117bad566478fcee540ded7, 
		//seckillId=2, 
		//now=0, 
		//start=0, 
		//end=0]
	}

	/**
	 * Test method for {@link com.seckill.service.SeckillService#executeSeckill(long, long, java.lang.String)}.
	 */
	/*@Test
	public void testExecuteSeckill() {
		long seckillId = 2;
		long userPhone = 13230941232L;
		String md5 = "1a3e4f425117bad566478fcee540ded7";
		try{
			SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
			log.info("execution={}",execution);
		}catch(RepeatKillException e){
			log.error(e.getMessage());
		}catch(SeckillException e){
			log.error(e.getMessage());
		}
		//SeckillExecution [
		//seckillId=2, 
		//state=1, stateInfo=秒杀成功, 
		//successKilled=SuccessKilled 
		//[seckillId=2, userPhone=13230941232, state=0, createTime=Sun Nov 06 22:51:33 CST 2016, seckill=SecKill [seckillId=0, name=500元秒杀ipad2, num=193, startTime=Sat Nov 05 00:12:00 CST 2016, endTime=Mon Nov 07 00:12:00 CST 2016, createTime=Sat Nov 05 17:59:47 CST 2016]]]
	}*/
}