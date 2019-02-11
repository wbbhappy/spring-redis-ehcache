package com.test;

import com.web.entity.User;
import com.web.util.SerializeUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest{
    private static Jedis jedis;
    static{
        //访问本地redis
        jedis = new Jedis("127.0.0.1",6379);
    }
    @Test
    public void serialize(){
        User user = new User("baby", "宝宝", "xioabao");
        jedis.set(user.getId().getBytes(), SerializeUtils.serialize(user));
        byte[] bytes = jedis.get(user.getId().getBytes());
        System.out.println((User)SerializeUtils.deSerialize(bytes));
    }
}
