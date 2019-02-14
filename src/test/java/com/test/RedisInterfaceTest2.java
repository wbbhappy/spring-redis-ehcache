package com.test;

import com.web.dao.impl.IUserDaoImpl2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试
 * @author http://blog.csdn.net/java2000_wl
 * @version <b>1.0</b>
 */
//@ContextConfiguration(locations = {"classpath*:applicationContext4.xml"})
public class RedisInterfaceTest2 {

    //@Autowired
    //IUserDao2 userDao2;
    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext4.xml");
    IUserDaoImpl2 userDao2 = ac.getBean(IUserDaoImpl2.class);

    @Test
    public void findAll() {
        System.out.println(userDao2.findAll());
    }

    @Test
    public void insert() {
        userDao2.insert();
    }
}
