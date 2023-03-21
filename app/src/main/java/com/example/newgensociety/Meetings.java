package com.example.newgensociety;

public class Meetings{


    String Subject, date, time, description;
    int no_of_members;

    public Meetings(){

    }

    public Meetings(String subject, int no_of_members, String date, String time, String description) {
        Subject = subject;
        this.date = date;
        this.time = time;
        this.description = description;
        this.no_of_members = no_of_members;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNo_of_members() {
        return no_of_members;
    }

    public void setNo_of_members(int no_of_members) {
        this.no_of_members = no_of_members;
    }
}
