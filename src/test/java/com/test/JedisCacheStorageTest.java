package com.test;

import com.web.entity.Users;
import com.web.jedis.RedisCacheStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-redis.xml"})
public class JedisCacheStorageTest {
    @Autowired
    private RedisCacheStorage<String,Users> redisCacheStorage;

    //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring-redis.xml");
    //RedisCacheStorageImpl redisCacheStorage = ac.getBean(RedisCacheStorageImpl.class);

    @Test
    public void testSet() throws Exception {
        System.out.println("开始执行set方法");
        Users users = new Users();
        users.setName("admin7");
        users.setPassword("admin8");
        System.out.println("users:" + users);
        redisCacheStorage.set("jedis",users);
    }

    @Test
    public void testGet() throws Exception {
        System.out.println("开始执行get方法");
        Users user2 = new Users();
        redisCacheStorage.get("jedis",user2);
    }

    @Test
    public void testRemove() throws Exception {
        System.out.println("开始执行remove方法");
        redisCacheStorage.remove("jedis");
        System.out.println("从redis成功删除数据！");
    }
}
