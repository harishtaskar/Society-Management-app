package com.example.newgensociety;

public class Helps {

    String name, flat_no, help, date;

    public Helps(){
    }

    public Helps(String name, String flat_no, String help, String date) {
        this.name = name;
        this.flat_no = flat_no;
        this.help = help;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
