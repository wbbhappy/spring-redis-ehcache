package com.test;

import com.web.dao.IUserDao2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 测试
 * @author http://blog.csdn.net/java2000_wl
 * @version <b>1.0</b>
 */
//@ContextConfiguration(locations = {"classpath*:applicationContext3.xml"})
public class RedisInterfaceTest2 {

    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext4.xml");
    IUserDao2 userDao2 = ac.getBean(IUserDao2.class);

    /**
     * 设置userDao
     * @param userDao the userDao to set
     */
    public void setUserDao(IUserDao2 userDao) {
        this.userDao2 = userDao2;
    }

    @Test
    public void findAll() {
        System.out.println(userDao2.findAll());
    }

    @Test
    public void insert() {
        System.out.println(userDao2.insert());
    }
}
