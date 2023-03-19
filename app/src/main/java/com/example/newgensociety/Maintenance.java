package com.example.newgensociety;

import java.util.Date;

public class Maintenance {
    String flat_no, discription;
    int amount, discount;
    String due_date;

    public Maintenance(){
    }

    public Maintenance(String flat_no, int amount, String due_date, String discription, int discount) {
        this.flat_no = flat_no;
        this.discription = discription;
        this.due_date = due_date;
        this.amount = amount;
        this.discount = discount;

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }


    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

}
