package com.project.model;

/**
 * Created by laishun on 2018/4/18.
 */
public class JobInfo {
    private int id;
    private String title;
    private String author;
    private String userId;
    private String jobType;
    private String time;
    private String content;
    private String audit;
    private String quality;

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getAudit() {

        return audit;
    }

    public String getQuality() {
        return quality;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobType() {
        return jobType;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {

        return userId;
    }

    private double progress;

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {

        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
