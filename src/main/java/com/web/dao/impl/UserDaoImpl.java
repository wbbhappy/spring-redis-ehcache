package com.web.dao.impl;

import com.web.dao.UserDao;
import com.web.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
	@Cacheable("users")
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
}
