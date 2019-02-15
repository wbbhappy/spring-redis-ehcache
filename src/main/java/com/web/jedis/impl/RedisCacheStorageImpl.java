package com.web.jedis.impl;

import com.web.jedis.RedisCacheStorage;
import com.web.util.JedisUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import java.util.HashMap;
import java.util.Map;

//@Service
@Repository
public class RedisCacheStorageImpl<V> implements RedisCacheStorage<String,V> {
    //日志记录
    private Logger log = LoggerFactory.getLogger(RedisCacheStorageImpl.class);
    /**
     * 默认过时时间
     */
    private static final int EXPRIE_TIME = 3600*24;
    /**
     * 获取Jedis相关操作
     */
    @Autowired
    private JedisUtil jedisUtil;

    public boolean set(String key, V value) {
        return set(key,value,EXPRIE_TIME);
    }

    public boolean set(String key, V value, int exp) {
        Jedis jedis = null;
        if(StringUtils.isEmpty(key)){
            return  false;
        }
        try {
            //获取jedis对象
            jedis = jedisUtil.getResource();
            System.out.println("成功从pool获取jedis实例！");
            //使用对象转换为Json格式插入redis
            JSONObject json = JSONObject.fromObject(value); //将java对象转换为json对象
            String jsonValue = json.toString();             //将json对象转换为json字符串
            System.out.println("键值对值：" + jsonValue);
            jedis.setex(key,exp,jsonValue);
            System.out.println("jedis成功插入数据到redis！");
        }catch (Exception e){
            //释放jedis对象
            jedisUtil.brokenResource(jedis);
            log.info("client can't connect server");
            return  false;
        }finally {
            //返还连接池
            jedisUtil.returnResource(jedis);
            return true;
        }
    }

    public V get(String key,Object object) {
        Jedis jedis = null;
        V v = null;
        if(StringUtils.isEmpty(key)){
            log.info("redis取值，key为空");
            return  null;
        }
        try{
            jedis = jedisUtil.getResource();    //获取连接
            String jsonValue = jedis.get(key);   //从redis得到值,得到的是json字符串，因为我们之前插入的时候是使用的json字符串
            if(StringUtils.isEmpty(jsonValue)){
                return  null;
            }
            JSONObject obj = new JSONObject().fromObject(jsonValue);//将json字符串转换为json对象
            v = (V)JSONObject.toBean(obj,object.getClass());        //将建json对象转换为你想要的java对象
            System.out.println("从redis取到的值为：" + v);
            return v;
        }catch (Exception e){
            //释放jedis对象
            if(jedis != null){
                jedisUtil.brokenResource(jedis);
            }
            log.info("client can't get value");
            return  null;
        }finally {
            //返还连接池
            jedisUtil.returnResource(jedis);
        }
    }

    public boolean remove(String key) {
        Jedis jedis = null;
        try{
            jedis = jedisUtil.getResource();
            if(StringUtils.isEmpty(key)){
                log.info("redis取值，key为空");
                return  false;
            }
            jedis.del(key);
        }catch (Exception e) {
            //释放jedis对象
            if(jedis != null){
                jedisUtil.brokenResource(jedis);
            }
            log.info("  del fail from redis");
            return false;
        }finally{
            //返还连接池
            jedisUtil.returnResource(jedis);
            return true;
        }
    }

    public boolean hset(String cacheKey, String key, V value) {
        Jedis jedis = null;
        //将key 和value  转换成 json 对象
        JSONObject json = JSONObject.fromObject(cacheKey);  //将java对象转换为json对象
        String jCacheKey = json.toString();                 //将json对象转换为json字符串
        JSONObject json2 = JSONObject.fromObject(value);    //将java对象转换为json对象
        String jsonValue = json2.toString();                //将json对象转换为json字符串
        //操作是否成功
        boolean isSucess = true;
        if(StringUtils.isEmpty(jCacheKey)){
            log.info("cacheKey is empty");
            return false;
        }
        try {
            jedis = jedisUtil.getResource();
            //执行插入哈希
            jedis.hset(jCacheKey, key, jsonValue);
        } catch (Exception e) {
            log.info("client can't connect server");
            isSucess = false;
            if(null != jedis){
                //释放jedis 对象
                jedisUtil.brokenResource(jedis);
            }
            return false;
        }finally{
            if (isSucess) {
                //返还连接池
                jedisUtil.returnResource(jedis);
            }
            return true;
        }
    }

    public V hget(String cacheKey, String key,Object object) {
        Jedis jedis = null;
        V v = null;
        JSONObject json = JSONObject.fromObject(cacheKey);  //将java对象转换为json对象
        String jCacheKey = json.toString();                 //将json对象转换为json字符串
        if(StringUtils.isEmpty(jCacheKey)){
            log.info("cacheKey is empty");
            return null;
        }
        try {
            //获取客户端对象
            jedis = jedisUtil.getResource();
            //执行查询
            String jsonValue = jedis.hget(jCacheKey, key);
            //判断值是否非空
            if(StringUtils.isEmpty(jsonValue)){
                return null;
            }else{
                JSONObject obj = new JSONObject().fromObject(jsonValue);//将json字符串转换为json对象
                v = (V)JSONObject.toBean(obj,object.getClass());        //将建json对象转换为java对象
            }
            //返还连接池
            jedisUtil.returnResource(jedis);
        } catch (JedisException e) {
            log.info("client can't connect server");
            if(null != jedis){
                //redisUtil 对象
                jedisUtil.brokenResource(jedis);
            }
        }
        return v;
    }

    public Map<String, V> hget(String cacheKey,Object object) {
        JSONObject json = JSONObject.fromObject(cacheKey);  //将java对象转换为json对象
        String jCacheKey = json.toString();                 //将json对象转换为字符串
        //非空校验
        if(StringUtils.isEmpty(jCacheKey)){
            log.info("cacheKey is empty!");
            return null;
        }
        Jedis jedis = null;
        Map<String,V> result = null;
        V v = null;
        try {
            jedis = jedisUtil.getResource();
            //获取列表集合 因为插入redis的时候是jsonString格式，所以取出来key是String value也是String
            Map<String,String> map = jedis.hgetAll(jCacheKey);
            if(null !=map){
                for(Map.Entry<String, String> entry : map.entrySet()){
                    if(result == null){
                        result = new HashMap<String,V>();
                    }
                    JSONObject obj = new JSONObject().fromObject(entry.getValue()); //将json字符串转换为json对象
                    v = (V)JSONObject.toBean(obj,object.getClass());                //将建json对象转换为java对象
                    result.put(entry.getKey(), v);
                }
            }
        } catch (JedisException e) {
            log.info("client can't connect server");
            if(null != jedis){
                //释放jedis 对象
                jedisUtil.brokenResource(jedis);
            }
        }
        return result;
    }
}
