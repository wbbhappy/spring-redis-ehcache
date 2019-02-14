package com.web.dao;

import com.web.entity.User;
import java.util.List;

public interface IUserDao2 {
    List<User> findAll();
    void insert();
}
