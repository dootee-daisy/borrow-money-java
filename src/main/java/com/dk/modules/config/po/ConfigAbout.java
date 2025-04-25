package com.dk.modules.config.po;

public class ConfigAbout {
    private String aboutId;
    private Integer aboutType;
    private String aboutContent;

    public String getAboutId() {
        return aboutId;
    }

    public void setAboutId(String aboutId) {
        this.aboutId = aboutId == null ? null : aboutId.trim();
    }

    public String getAboutContent() {
        return aboutContent;
    }

    public void setAboutContent(String aboutContent) {
        this.aboutContent = aboutContent == null ? null : aboutContent.trim();
    }

    public Integer getAboutType() {
        return aboutType;
    }

    public void setAboutType(Integer aboutType) {
        this.aboutType = aboutType;
    }
}