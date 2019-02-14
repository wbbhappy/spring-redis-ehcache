package com.test;

import com.web.entity.User;
import com.web.util.SerializeUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import java.util.List;

public class JedisListTest {
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

    @Test
    public void jedis(){
        //连接本地的 Redis 服务
        //Jedis jedis = new Jedis("localhost");
        //System.out.println("连接成功");
        //存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("site-list", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: " + list.get(i));
        }
    }
}

