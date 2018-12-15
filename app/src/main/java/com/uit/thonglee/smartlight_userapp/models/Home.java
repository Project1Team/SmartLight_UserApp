package com.uit.thonglee.smartlight_userapp.models;

import java.util.ArrayList;
import java.util.List;

public class Home {
    public String name;
    public String macAddr;
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

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public Home(String name, String macAddr, List<Device> devices) {
        this.name = name;
        this.macAddr = macAddr;
        this.devices = devices;
    }

    public Home() {
        this.name = "";
        this.macAddr = "";
        this.devices = new ArrayList<Device>();
    }
}