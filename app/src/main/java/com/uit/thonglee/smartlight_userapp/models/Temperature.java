package com.uit.thonglee.smartlight_userapp.models;

public class Temperature extends Device{
    public String value;

    public Temperature(String name, String type, String value) {
        super(name, type);
        this.value = value;
    }

    public Temperature(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
