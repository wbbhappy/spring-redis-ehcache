package com.web.service.impl;

import com.web.entity.User;
import com.web.service.UserDao;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
	@Cacheable("userCache")
	public User get(String keyId) {
		User user = new User();
		user.setId("1");
		user.setName("xiaoming");
		user.setPassword("322424");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return user;
	}
	@Cacheable("userCache")
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
	 * 用于查询的注解，第一次查询的时候返回该方法返回值，并向redis服务器保存数据，
	 * 以后的查询将不再执行方法体内的代码，而是直接查询redis服务器获取数据并返回。
	 * value属性做键，key属性则可以看作为value的子键，
	 * 一个value可以有多个key组成不同值存在redis服务器，
	 * 这里再介绍一个属性是condition，用法condition="#key<10"，
	 * 就是说当key小于10的时候才将数据保存到redis服务器
	 */
	@Cacheable(value="redis",key="#key", condition="#key<10")
	public String cacheable(int key) throws Exception {
		return "cacheable";
	}

	/**
	 * 用于更新数据库或新增数据时的注解，更新redis服务器中该value的值
	 */
	@CachePut(value="redis",key="#key", condition="#key<10")
	public String cachePut(String key) throws Exception {
		return "cachePut";
	}

	/**
	 * 用于删除数据操作时的注解，删除redis数据库中该value对应的数据
	 */
	@CacheEvict(value="redis", key="#key")
	public String cacheEvict(String key) throws Exception {
		return "cacheEvict";
	}
}
