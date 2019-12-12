package com.shubamvirdi.internshala.ModelClasses;

import java.io.Serializable;

public class WorkshopModel implements Serializable {
    private String location,time,title,subtitle,date;
    private int uid;

    public WorkshopModel(){

    }


    public WorkshopModel(String location, String time, String title, String subtitle,int uid,String date) {
        this.location = location;
        this.time = time;
        this.date = date;
        this.title = title;
        this.subtitle = subtitle;
        this.uid = uid;
    }


    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
