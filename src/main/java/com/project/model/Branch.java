package com.project.model;

/**
 * Created by laishun on 2018/4/17.
 */

/**
 * 支部详情类
 */
public class Branch {
    private int    id;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    private String details;
    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {

        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDetails() {
        return details;
    }
}
