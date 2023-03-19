package com.example.newgensociety;

import java.util.Date;

public class Maintenance {
    String flat_no,  discription;
    Date due_date;
    Integer amount, discount;

    public Maintenance(){

    }

    public Maintenance(String flat_no, Integer amount, Date due_date, String discription, Integer discount) {
        this.flat_no = flat_no;
        this.amount = amount;
        this.due_date = due_date;
        this.discription = discription;
        this.discount = discount;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
