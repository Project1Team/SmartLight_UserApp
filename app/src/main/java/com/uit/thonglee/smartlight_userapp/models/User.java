package com.uit.thonglee.smartlight_userapp.models;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class User {
    public ObjectId id;
    public String name;
    public String password;
    public List<Room> rooms;

    public User(ObjectId id, String name, String password, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.rooms = rooms;
    }

    public User() {
        this.name = "";
        this.password = "";
        this.rooms = new ArrayList<Room>();
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}