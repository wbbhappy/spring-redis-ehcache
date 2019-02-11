package com.test;

import com.web.service.RedisCacheStorage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class RedisCacheStorageTest {
    @Autowired
    //private RedisCacheStorage<String,Users> redisCacheStorage;
    private RedisCacheStorage<String,String> redisCacheStorage;

    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext2.xml,spring-redis2.xml");
    //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring-redis2.xml");
    //RedisCacheStorage redisCacheStorage = ac.getBean(RedisCacheStorage.class);

    @Test
    public void testSetGet() throws Exception {
        System.out.print("开始执行测试");
        /*Users users = new Users();
        users.setName("admin7");
        users.setPassword("admin8");
        System.out.print("users:" + users);
        redisCacheStorage.set("Akey7",users);
        Users user2 = redisCacheStorage.get("Akey7",new Users());
        System.out.print("=======" + user2.getName() + "=====" + user2.getPassword());*/

        redisCacheStorage.set("Akey7","wbbhappy");
    }
}
