package com.example.newgensociety;

import android.content.Intent;

public class Flats {

    public Flats() {
    }
    int Flat_No;
    String Name, Mobile, Date, Status;



    public Flats(int flat_No, String name, String mobile, String date, String status) {
        Flat_No = flat_No;
        Name = name;
        Mobile = mobile;
        Date = date;
        Status = status;
    }

    public int getFlat_No() {
        return Flat_No;
    }

    public void setFlat_No(int flat_No) {
        Flat_No = flat_No;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
