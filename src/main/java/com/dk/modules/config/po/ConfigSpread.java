package com.dk.modules.config.po;

import java.util.Date;

public class ConfigSpread {
    private String id;

    private String areas;

    private Integer status;

    private Date startTime;

    private Date endTime;

    private String insidePath;

    private String outsidePath;

    private String iosPath;

    private String androidPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas == null ? null : areas.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getInsidePath() {
        return insidePath;
    }

    public void setInsidePath(String insidePath) {
        this.insidePath = insidePath == null ? null : insidePath.trim();
    }

    public String getOutsidePath() {
        return outsidePath;
    }

    public void setOutsidePath(String outsidePath) {
        this.outsidePath = outsidePath == null ? null : outsidePath.trim();
    }

    public String getIosPath() {
        return iosPath;
    }

    public void setIosPath(String iosPath) {
        this.iosPath = iosPath == null ? null : iosPath.trim();
    }

    public String getAndroidPath() {
        return androidPath;
    }

    public void setAndroidPath(String androidPath) {
        this.androidPath = androidPath == null ? null : androidPath.trim();
    }
}