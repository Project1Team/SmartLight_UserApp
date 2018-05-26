package com.uit.thonglee.smartlight_userapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class User {
    public ObjectId id;
    public String name;
    public String password;
    public List<Home> home;

    public User(ObjectId id, String name, String password, List<Home> home) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.home = home;
    }

    public User() {
        this.name = "";
        this.password = "";
        this.home = new ArrayList<Home>();
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

    public List<Home> getHome() {
        return home;
    }

    public void setHome(List<Home> home) {
        this.home = home;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}