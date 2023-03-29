package com.example.newgensociety;

public class S_Member {

    public S_Member() {
    }
    String S_name, Mobile, Email, Password;
    boolean SM_Status;


    public S_Member(String s_Name, String mobile, String email, String password, boolean SM_Status) {
        S_name = s_Name;
        Mobile = mobile;
        Email = email;
        Password = password;
        this.SM_Status = SM_Status;

    }

    public boolean isSM_Status() {
        return SM_Status;
    }

    public void setSM_Status(boolean SM_Status) {
        this.SM_Status = SM_Status;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String s_name) {
        S_name = s_name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
