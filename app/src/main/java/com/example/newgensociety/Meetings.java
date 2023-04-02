package com.example.newgensociety;

public class Meetings{


    String Subject, date, time, description, meetingCode;
    boolean removed;
    int no_of_members;

    public Meetings(){

    }

    public Meetings(String subject, int no_of_members, String date, String time, String description, String meetingCode, boolean removed) {
        Subject = subject;
        this.date = date;
        this.time = time;
        this.description = description;
        this.no_of_members = no_of_members;
        this.meetingCode = meetingCode;
        this.removed = removed;
    }

    public String getMeetingCode() {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode) {
        this.meetingCode = meetingCode;
    }

    public boolean getRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
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
