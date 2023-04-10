package com.example.newgensociety;

public class Hall {

    public Hall(){

    }
    String hall_title, hall_size, Description;

    public Hall(String hall_title, String hall_size, String description) {
        this.hall_title = hall_title;
        this.hall_size = hall_size;
        Description = description;
    }

    public String getHall_title() {
        return hall_title;
    }

    public void setHall_title(String hall_title) {
        this.hall_title = hall_title;
    }

    public String getHall_size() {
        return hall_size;
    }

    public void setHall_size(String hall_size) {
        this.hall_size = hall_size;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
