package com.uit.thonglee.smartlight_userapp.models;

public class Fire extends Device{
    public String status;

    public Fire(String name, String type, String status) {
        super(name, type);
        this.status = status;
    }

    public Fire(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
