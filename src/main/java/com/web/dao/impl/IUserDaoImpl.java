package com.web.dao.impl;

import com.web.dao.AbstractBaseRedisDao;
import com.web.dao.IUserDao;
import com.web.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import java.util.ArrayList;
import java.util.List;

public class IUserDaoImpl extends AbstractBaseRedisDao<String, User> implements IUserDao {

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
    /**
     * 新增
     * @param user
     * @return
     */
    public boolean add(final User user) {
        System.out.println("user:" + user);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(user.getId());
                byte[] name = serializer.serialize(user.getName());
                return connection.setNX(key, name);
            }
        });
        return result;
    }
    /**
     * 批量新增 使用pipeline方式
     *@param list
     *@return
     */
    public boolean add(final List<User> list) {
        Assert.notEmpty(list);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (User user : list) {
                    byte[] key  = serializer.serialize(user.getId());
                    byte[] name = serializer.serialize(user.getName());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);
        return result;
    }
    /**
     * 删除
     * @param key
     */
    public void delete(String key) {
        List<String> list = new ArrayList<String>();
        list.add(key);
        delete(list);
    }
    /**
     * 删除多个
     * @param keys
     */
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }
    /**
     * 修改
     * @param user
     * @return
     */
    public boolean update(final User user) {
        String key = user.getId();
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(user.getId());
                byte[] name = serializer.serialize(user.getName());
                connection.set(key, name);
                return true;
            }
        });
        return result;
    }
    /**
     * 通过key获取
     * @param keyId
     * @return
     */
    public User get(final String keyId) {
        User result = redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String name = serializer.deserialize(value);
                return new User(keyId, name, null);
            }
        });
        return result;
    }
}
