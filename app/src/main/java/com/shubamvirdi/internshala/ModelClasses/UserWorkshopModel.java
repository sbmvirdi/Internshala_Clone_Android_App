package com.shubamvirdi.internshala.ModelClasses;

public class UserWorkshopModel {
    private int uid;
    private String email,workshop_name;


    public UserWorkshopModel(int uid, String email, String workshop_name) {
        this.uid = uid;
        this.email = email;
        this.workshop_name = workshop_name;
    }


    public UserWorkshopModel() {
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

    public String getWorkshop_name() {
        return workshop_name;
    }

    public void setWorkshop_name(String workshop_name) {
        this.workshop_name = workshop_name;
    }
}
