package com.project.model;

/**
 * Created by laishun on 2018/3/15.
 */
public class House {

    private int id;
    private String area;
    private String layer;
    private String room;
    private String structure;
    private String address;
    private String title;
    private String remark;
    private double rent;
    private int    landlord;
    private int    tenant;
    private String picture;
    private int    status;
    private String time;
    private double PayedRent;

    public double getPayedRent() {
        return PayedRent;
    }

    public void setPayedRent(double payedRent) {
        PayedRent = payedRent;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getPicture() {
        return picture;
    }
    public int getTenant() {
        return tenant;
    }
    public void setTenant(int tenant) {
        this.tenant = tenant;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public void setLandlord(int landlord) {
        this.landlord = landlord;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public String getLayer() {
        return layer;
    }

    public String getRoom() {
        return room;
    }

    public String getStructure() {
        return structure;
    }

    public String getAddress() {
        return address;
    }

    public String getTitle() {
        return title;
    }

    public String getRemark() {
        return remark;
    }

    public double getRent() {
        return rent;
    }

    public int getLandlord() {
        return landlord;
    }

    public String getTime() {
        return time;
    }
}
