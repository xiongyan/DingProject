package com.project.model;

import java.util.List;

/**
 * Created by laishun on 2018/4/18.
 */
public class Record {

    private String detailPlace;
    private double latitude;
    private String name;
    private String remark;
    private String place;
    private String userId;
    private long   timestamp;
    private double longitude;
    private List<String> imageList;

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getPlace() {
        return place;
    }

    public String getUserId() {
        return userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getImageList() {
        return imageList;
    }
}
