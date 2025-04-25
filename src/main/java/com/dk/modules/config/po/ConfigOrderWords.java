package com.dk.modules.config.po;

public class ConfigOrderWords {
    private String tipsId;

    private String tipsName;

    private String loanOrderContent;

    private Integer loanOrderStatus;

    private String orderMessageContent;

    private Integer orderMessageStatus;

    private String withdrawContent;

    private Integer withdrawStatus;

    private Integer deleted;

    public String getTipsId() {
        return tipsId;
    }

    public void setTipsId(String tipsId) {
        this.tipsId = tipsId == null ? null : tipsId.trim();
    }

    public String getTipsName() {
        return tipsName;
    }

    public void setTipsName(String tipsName) {
        this.tipsName = tipsName == null ? null : tipsName.trim();
    }

    public String getLoanOrderContent() {
        return loanOrderContent;
    }

    public void setLoanOrderContent(String loanOrderContent) {
        this.loanOrderContent = loanOrderContent == null ? null : loanOrderContent.trim();
    }

    public Integer getLoanOrderStatus() {
        return loanOrderStatus;
    }

    public void setLoanOrderStatus(Integer loanOrderStatus) {
        this.loanOrderStatus = loanOrderStatus;
    }

    public String getOrderMessageContent() {
        return orderMessageContent;
    }

    public void setOrderMessageContent(String orderMessageContent) {
        this.orderMessageContent = orderMessageContent == null ? null : orderMessageContent.trim();
    }

    public Integer getOrderMessageStatus() {
        return orderMessageStatus;
    }

    public void setOrderMessageStatus(Integer orderMessageStatus) {
        this.orderMessageStatus = orderMessageStatus;
    }

    public String getWithdrawContent() {
        return withdrawContent;
    }

    public void setWithdrawContent(String withdrawContent) {
        this.withdrawContent = withdrawContent == null ? null : withdrawContent.trim();
    }

    public Integer getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(Integer withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}