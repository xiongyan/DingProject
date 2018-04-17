package com.project.model;

/**
 * Created by laishun on 2018/4/4.
 */
public class Account {

    private int userId;
    private double total;
    private String flag;
    private String time;
    private String remark;

    public void setTime(String time) {
        this.time = time;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {

        return time;
    }

    public String getRemark() {
        return remark;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotal() {
        return total;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
