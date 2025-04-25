package com.dk.modules.order.po;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private String orderId;

    private String phone;

    private String name;

    private String loanDeadline;

    private String loanPurpose;

    private String loanRate;

    private String sumLoan;

    private String monthlyPayments;
    private String monthPayDay;

    private Date createDate;

    private String status;

    private String statusName;

    private String statusContent;

    private BigDecimal wallet;

    private String repaymentAmount;

    private Date repaymentDate;

    private Integer deleted;

    private String drawCode;

    private String bankName;

    private String bankCard;

    private String bankCardInit;

    private Integer bankCardUpdate;

    private String sumLoanInit;

    private Integer sumLoanUpdate;

    private String channelCode;
    private String channelName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLoanDeadline() {
        return loanDeadline;
    }

    public void setLoanDeadline(String loanDeadline) {
        this.loanDeadline = loanDeadline == null ? null : loanDeadline.trim();
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose == null ? null : loanPurpose.trim();
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate == null ? null : loanRate.trim();
    }

    public String getSumLoan() {
        return sumLoan;
    }

    public void setSumLoan(String sumLoan) {
        this.sumLoan = sumLoan == null ? null : sumLoan.trim();
    }

    public String getMonthlyPayments() {
        return monthlyPayments;
    }

    public void setMonthlyPayments(String monthlyPayments) {
        this.monthlyPayments = monthlyPayments == null ? null : monthlyPayments.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent == null ? null : statusContent.trim();
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount == null ? null : repaymentAmount.trim();
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getDrawCode() {
        return drawCode;
    }

    public void setDrawCode(String drawCode) {
        this.drawCode = drawCode == null ? null : drawCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getBankCardInit() {
        return bankCardInit;
    }

    public void setBankCardInit(String bankCardInit) {
        this.bankCardInit = bankCardInit == null ? null : bankCardInit.trim();
    }

    public Integer getBankCardUpdate() {
        return bankCardUpdate;
    }

    public void setBankCardUpdate(Integer bankCardUpdate) {
        this.bankCardUpdate = bankCardUpdate;
    }

    public String getSumLoanInit() {
        return sumLoanInit;
    }

    public void setSumLoanInit(String sumLoanInit) {
        this.sumLoanInit = sumLoanInit == null ? null : sumLoanInit.trim();
    }

    public Integer getSumLoanUpdate() {
        return sumLoanUpdate;
    }

    public void setSumLoanUpdate(Integer sumLoanUpdate) {
        this.sumLoanUpdate = sumLoanUpdate;
    }

    public String getMonthPayDay() {
        return monthPayDay;
    }

    public void setMonthPayDay(String monthPayDay) {
        this.monthPayDay = monthPayDay;
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