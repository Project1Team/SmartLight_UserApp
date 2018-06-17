package com.uit.thonglee.smartlight_userapp.models;

public class Device {
    public String name;
    public String macAddr;
    public String color;
    public String brightness;
    public int status;

    public Device(String name, String macAddr, String color, String brightness, int status) {
        this.name = name;
        this.macAddr = macAddr;
        this.color = color;
        this.brightness = brightness;
        this.status = status;
    }
    public Device(){
        this.name = "";
        this.macAddr = "";
        this.color = "";
        this.brightness = "";
        this.status = 0;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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