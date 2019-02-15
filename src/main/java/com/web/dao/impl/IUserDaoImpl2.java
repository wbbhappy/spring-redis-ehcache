package com.web.dao.impl;

import com.web.dao.IUserDao2;
import com.web.entity.User;
import com.web.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IUserDaoImpl2 implements IUserDao2 {

    @Autowired
    RedisUtil redisUtil;

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

    public void insert() {
        String key = "user";
        String value = "tom";
        this.redisUtil.set("user", "tom");
        /*String result = this.redisUtils.get("user");
        return result;*/
    }
}
