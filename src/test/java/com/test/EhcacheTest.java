package com.test;

import com.web.service.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EhcacheTest {
	@Test
	public void testEhcache(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		System.out.println("ApplicationContext" + ac);

		UserDao userDao = ac.getBean(UserDao.class);
		System.out.println("userDao" + userDao);

		/**
		 * @Cacheable("users")Ehcache的注解，redis缓存无法调用
		 */
		System.out.println("第1次");
		long st = System.currentTimeMillis();
		System.out.println(userDao.get("1"));
		long end = System.currentTimeMillis();
		System.out.println("时间" + (end-st));
		
		System.out.println("第2次");
		long st1 = System.currentTimeMillis();
		System.out.println(userDao.get("1"));
		long end1 = System.currentTimeMillis();
		System.out.println("时间" + (end1-st1));
		
		System.out.println("第3次");
		long st2 = System.currentTimeMillis();
		System.out.println(userDao.get("1"));
		long end2 = System.currentTimeMillis();
		System.out.println("时间" + (end2-st2));
		
		System.out.println("第4次");
		long st3 = System.currentTimeMillis();
		System.out.println(userDao.get("1"));
		long end3 = System.currentTimeMillis();
		System.out.println("时间" + (end3-st3));
		
		System.out.println("第5次");
		long st4 = System.currentTimeMillis();
		System.out.println(userDao.get("1"));
		long end4 = System.currentTimeMillis();
		System.out.println("时间" + (end4-st4));
		
		System.out.println("第6次");
		long st5 = System.currentTimeMillis();
		System.out.println(userDao.get("1"));
		long end5 = System.currentTimeMillis();
		System.out.println("时间" + (end5-st5));

		/**
		 * @Cacheable("userCache")redis的注解，Ehcache缓存无法调用
		 */
		System.out.println("第7次");
		long st7 = System.currentTimeMillis();
		System.out.println(userDao.findAll());
		long end7 = System.currentTimeMillis();
		System.out.println("时间" + (end7-st7));
	}
}
