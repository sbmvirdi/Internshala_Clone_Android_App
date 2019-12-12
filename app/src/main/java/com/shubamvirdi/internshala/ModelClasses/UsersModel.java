package com.shubamvirdi.internshala.ModelClasses;

public class UsersModel {
    private int uid;
    private String email,pass;

    public UsersModel(int uid, String email, String pass) {
        this.uid = uid;
        this.email = email;
        this.pass = pass;
    }

    public UsersModel(){

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
