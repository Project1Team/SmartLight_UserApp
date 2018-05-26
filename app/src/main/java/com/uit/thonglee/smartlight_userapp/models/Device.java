package com.uit.thonglee.smartlight_userapp.models;

public class Device {
    public String name;
    public String macAddr;
    public String color;

    public Device(String name, String macAddr, String color) {
        this.name = name;
        this.macAddr = macAddr;
        this.color = color;
    }
    public Device(){
        this.name = "";
        this.macAddr = "";
        this.color = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}