package com.project.model;

/**
 * Created by laishun on 2018/4/19.
 */
public class Audit {
    private int     id;
    private int     jobInfoId;
    private String  time;
    private String  auditUser;
    private String  createUser;
    private String  current;
    private boolean isFinish;
    private String  type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {

        return type;
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

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
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

    public String getAuditUser() {
        return auditUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getCurrent() {
        return current;
    }

    public boolean isFinish() {
        return isFinish;
    }
}
