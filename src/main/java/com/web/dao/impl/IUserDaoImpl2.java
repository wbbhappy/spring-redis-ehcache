package com.web.dao.impl;

import com.web.dao.IUserDao2;
import com.web.entity.User;
import com.web.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

public class IUserDaoImpl2 implements IUserDao2 {

    @Autowired
    RedisUtils redisUtil;

    public List<User> findAll() {
        List<User> list = new ArrayList<User>();
        for(int i=0;i<5;i++){
            User user = new User();
            user.setId("0" + i);
            user.setName("xiaoming" + i);
            user.setPassword("ssd" + i);
            list.add(user);
        }
        return list;
    }

    public String insert() {
        String key = "user";
        String value = "tom";
        redisUtil.set(key, value);
        String result = redisUtil.get(key);
        return result;
    }
}
