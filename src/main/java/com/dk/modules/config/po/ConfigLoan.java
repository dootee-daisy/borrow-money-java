package com.dk.modules.config.po;

public class ConfigLoan {
    private String lId;

    private Integer minAmount;

    private Integer maxAmount;

    private String rate;

    private String repaymentDay;

    private String allowMonth;

    private String defMonth;

    private Integer defAmount;

    public String getlId() {
        return lId;
    }

    public void setlId(String lId) {
        this.lId = lId == null ? null : lId.trim();
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public String getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(String repaymentDay) {
        this.repaymentDay = repaymentDay == null ? null : repaymentDay.trim();
    }

    public String getAllowMonth() {
        return allowMonth;
    }

    public void setAllowMonth(String allowMonth) {
        this.allowMonth = allowMonth == null ? null : allowMonth.trim();
    }

    public String getDefMonth() {
        return defMonth;
    }

    public void setDefMonth(String defMonth) {
        this.defMonth = defMonth == null ? null : defMonth.trim();
    }

    public Integer getDefAmount() {
        return defAmount;
    }

    public void setDefAmount(Integer defAmount) {
        this.defAmount = defAmount;
    }
}