package com.uit.thonglee.smartlight_userapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class User {
    public ObjectId id;
    public String name;
    public String password;
    public List<Home> homes;

    public User(ObjectId id, String name, String password, List<Home> homes) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.homes = homes;
    }

    public User() {
        this.name = "";
        this.password = "";
        this.homes = new ArrayList<Home>();
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

    public List<Home> getHomes() {
        return homes;
    }

    public void setHomes(List<Home> homes) {
        this.homes = homes;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}