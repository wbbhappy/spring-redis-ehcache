package com.web.dao;

import com.web.entity.User;
import java.util.List;

public interface UserDao {
    User get(String keyId); 
    List<User> findAll();
}
