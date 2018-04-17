package com.project.model;

/**
 * Created by laishun on 2018/3/15.
 */
public class User {

    private int id;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String phone;
    private int status;
    private String role; //三种角色：tenant租客、landlord房东、manager系统管理人员
    private String remark;
    private String idCard;
    private String token;
    private String insertDate;

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public String getRemark() {
        return remark;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getToken() {
        return token;
    }
}
