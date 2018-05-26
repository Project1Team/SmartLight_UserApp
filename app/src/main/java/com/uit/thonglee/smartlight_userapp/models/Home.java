package com.uit.thonglee.smartlight_userapp.models;

import java.util.ArrayList;
import java.util.List;

public class Home {
    public String name;
    public List<Device> devices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Home(String name, List<Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public Home() {
        this.name = "";
        this.devices = new ArrayList<Device>();
    }
}