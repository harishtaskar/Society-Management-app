package com.example.newgensociety;

public class Hall_Request {
    public Hall_Request(){

    }

    String hall_title, date, description, name, time;
    boolean isApproved;

    public Hall_Request(String hall_Title, String date, String description, String name, String time, boolean isApproved) {
        this.hall_title = hall_Title;
        this.date = date;
        this.description = description;
        this.name = name;
        this.time = time;
        this.isApproved = isApproved;
    }

    public String getHall_title() {
        return hall_title;
    }

    public void setHall_title(String hall_title) {
        this.hall_title = hall_title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
