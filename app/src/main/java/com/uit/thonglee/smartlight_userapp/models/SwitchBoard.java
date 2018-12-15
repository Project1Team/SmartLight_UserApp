package com.uit.thonglee.smartlight_userapp.models;

import java.util.List;

public class SwitchBoard extends Device {
    public List<Switch> switchs;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String index;

    public SwitchBoard(String name, String type, String index, List<Switch> switchs) {
        super(name, type);
        this.switchs = switchs;
        this.index = index;
    }

    public SwitchBoard(List<Switch> switchs, String index) {
        this.switchs = switchs;
        this.index = index;
    }

    public List<Switch> getSwitchs() {
        return switchs;
    }

    public void setSwitchs(List<Switch> switchs) {
        this.switchs = switchs;
    }
}
