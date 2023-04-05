package com.example.newgensociety;

import android.content.Intent;

public class Flats {
    public Flats() {
    }
    int flat_no;
    String name, mobile, date, status, userId;

    public Flats(int flat_No, String name, String mobile, String date, String status, String userId) {
        flat_no = flat_No;
        name = name;
        mobile = mobile;
        date = date;
        status = status;
        userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(int flat_no) {
        this.flat_no = flat_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
