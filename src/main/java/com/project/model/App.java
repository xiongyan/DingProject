package com.project.model;

/**
 * Created by laishun on 2018/4/11.
 */
public class App {
    private String  appIcon;
    private int     agentId;
    private String  appDesc;
    private String  pcHomepageLink;
    private String  name;
    private String  homepageLink;
    private int     appStatus;
    private boolean isSelf;
    private String  ompLink;

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public void setPcHomepageLink(String pcHomepageLink) {
        this.pcHomepageLink = pcHomepageLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomepageLink(String homepageLink) {
        this.homepageLink = homepageLink;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }

    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public void setOmpLink(String ompLink) {
        this.ompLink = ompLink;
    }

    public String getAppIcon() {

        return appIcon;
    }

    public int getAgentId() {
        return agentId;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public String getPcHomepageLink() {
        return pcHomepageLink;
    }

    public String getName() {
        return name;
    }

    public String getHomepageLink() {
        return homepageLink;
    }

    public int getAppStatus() {
        return appStatus;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public String getOmpLink() {
        return ompLink;
    }
}
