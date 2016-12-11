package test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;

/**
 * @author xiaobai
 * @date 2016年11月6日上午12:06:28
 */
/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//注入DAO实现类依赖
	@Resource//j2ee自带的注解，与spring中@Autowired注解类型
	private SeckillDao seckilldao;
	
	@Test
	public void testQueryById() throws Exception{
		long id = 2;
		Seckill seckill = seckilldao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() throws Exception{
		//java没有保存形参的记录
		List<Seckill> list = seckilldao.queryAll(1, 4);
		for(Seckill a : list){
			System.out.println(a);
		}
	}
	
	@Test
	public void testReduceNum() throws Exception{
		Date date = new Date();
		int i = seckilldao.reduceNum(2, date);
		System.out.println("updateCount:"+i);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void killByProcedure(){
		Date killTime = new Date();
		Map map = new HashMap<>();
		map.put("seckill", 2);
		map.put("phone", "13333333333");
		map.put("killTime", killTime);
		System.out.println(killTime);
		try {
			seckilldao.killByProcedure(map);
			int result = MapUtils.getInteger(map, "result", -2);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}