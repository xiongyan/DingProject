package com.project.model;

/**
 * Created by laishun on 2018/4/19.
 */
public class Audit {
    private int     id;
    private int     jobInfoId;
    private String  time;
    private String  userName;
    private String  userId;
    private boolean townAuth;
    private boolean countyAuth;
    private boolean cityAuth;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJobInfoId(int jobInfoId) {
        this.jobInfoId = jobInfoId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTownAuth(boolean townAuth) {
        this.townAuth = townAuth;
    }

    public void setCountyAuth(boolean countyAuth) {
        this.countyAuth = countyAuth;
    }

    public void setCityAuth(boolean cityAuth) {
        this.cityAuth = cityAuth;
    }

    public int getId() {

        return id;
    }

    public int getJobInfoId() {
        return jobInfoId;
    }

    public String getTime() {
        return time;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isTownAuth() {
        return townAuth;
    }

    public boolean isCountyAuth() {
        return countyAuth;
    }

    public boolean isCityAuth() {
        return cityAuth;
    }
}
