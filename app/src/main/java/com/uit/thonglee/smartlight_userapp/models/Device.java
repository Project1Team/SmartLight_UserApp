package com.uit.thonglee.smartlight_userapp.models;

public class Device {
    public String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String type;

    public Device(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public Device(){
        this.name = "";
        this.type = "";
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}