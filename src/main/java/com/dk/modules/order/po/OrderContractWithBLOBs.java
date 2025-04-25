package com.dk.modules.order.po;

public class OrderContractWithBLOBs extends OrderContract {
    private String agreement;

    private String sign;

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement == null ? null : agreement.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }
}