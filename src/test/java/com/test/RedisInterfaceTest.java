package com.test;

import com.web.dao.IUserDao;
import com.web.entity.User;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;
import java.util.List;
/**
 * 测试
 * @author http://blog.csdn.net/java2000_wl
 * @version <b>1.0</b>
 */
//@ContextConfiguration(locations = {"classpath*:applicationContext3.xml"})
public class RedisInterfaceTest {

    ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext3.xml");
    IUserDao userDao = ac.getBean(IUserDao.class);

    /**
     * 设置userDao
     * @param userDao the userDao to set
     */
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Test
    public void findAll() {
        System.out.println(userDao.findAll());
    }

    @Test
    public void insert() {
        System.out.println(userDao.insert());
    }

    /**
     * 新增
     */
    @Test
    public void testAddUser() {
        User user = new User();
        user.setId("user1");
        user.setName("java2000_wl");
        boolean result = userDao.add(user);
        Assert.assertTrue(result);
    }
    /**
     * 批量新增 普通方式
     */
    @Test
    public void testAddUsers1() {
        List<User> list = new ArrayList<User>();
        for (int i = 10; i < 50000; i++) {
            User user = new User();
            user.setId("user" + i);
            user.setName("java2000_wl" + i);
            list.add(user);
        }
        long begin = System.currentTimeMillis();
        for (User user : list) {
            userDao.add(user);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
    /**
     * 批量新增 pipeline方式
     */
    @Test
    public void testAddUsers2() {
        List<User> list = new ArrayList<User>();
        for (int i = 10; i < 1500000; i++) {
            User user = new User();
            user.setId("user" + i);
            user.setName("java2000_wl" + i);
            list.add(user);
        }
        long begin = System.currentTimeMillis();
        boolean result = userDao.add(list);
        System.out.println(System.currentTimeMillis() - begin);
        Assert.assertTrue(result);
    }
    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        User user = new User();
        user.setId("user1");
        user.setName("new_password");
        boolean result = userDao.update(user);
        Assert.assertTrue(result);
    }
    /**
     * 通过key删除单个
     */
    @Test
    public void testDelete() {
        String key = "user1";
        userDao.delete(key);
    }
    /**
     * 批量删除
     */
    @Test
    public void testDeletes() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("user" + i);
        }
        userDao.delete(list);
    }
    /**
     * 获取
     */
    @Test
    public void testGetUser() {
        String id = "user1";
        User user = userDao.get(id);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), "java2000_wl");
    }
}

