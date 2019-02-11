package com.web.entity;

import java.io.Serializable;

public class Users implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String password;

    public Users(){};
    public Users(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User [name=" + name + ", password=" + password + "]";
    }
}
