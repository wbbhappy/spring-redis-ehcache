package com.test;

import com.web.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-redis3.xml"})
public class RedisTest{
    @Autowired(required=false)
    private RedisUtils redisUtils;

    /*@Autowired(required=false)
    private RedisUtil redisUtil;*/

    @Test
    public void testSet() {
        this.redisUtils.set("baby", "baby");
        System.out.println("redis插入数据成功！");
    }

    @Test
    public void testGet() {
        this.redisUtils.get("baby");
        System.out.println("redis读取数据成功！");
    }
}
