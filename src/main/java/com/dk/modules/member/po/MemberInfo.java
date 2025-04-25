package com.dk.modules.member.po;

import java.math.BigDecimal;
import java.util.Date;

public class MemberInfo {
    private String id;

    private String password;

    private String name;

    private String idcard;

    private Integer level;

    private Integer idInfo;

    private Integer basicInfo;

    private Integer bankInfo;

    private String idcardFront;

    private String idcardBack;

    private String idcardHand;

    private Date addTime;

    private Date updateTime;

    private Integer status;

    private Integer deleted;

    private BigDecimal balance;

    private BigDecimal withdrawal;

    private String channelCode;
    private String channelName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(Integer idInfo) {
        this.idInfo = idInfo;
    }

    public Integer getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(Integer basicInfo) {
        this.basicInfo = basicInfo;
    }

    public Integer getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(Integer bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront == null ? null : idcardFront.trim();
    }

    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack == null ? null : idcardBack.trim();
    }

    public String getIdcardHand() {
        return idcardHand;
    }

    public void setIdcardHand(String idcardHand) {
        this.idcardHand = idcardHand == null ? null : idcardHand.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(BigDecimal withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}