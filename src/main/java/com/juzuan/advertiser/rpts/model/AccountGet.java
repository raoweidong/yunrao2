package com.juzuan.advertiser.rpts.model;

public class AccountGet {
    private Integer id;

    private String taobaoUserId;

    private String banlance;

    private String grantBalance;

    private String cashBalance;

    private String availableBalance;

    private String creditBalance;

    private String redPacket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaobaoUserId() {
        return taobaoUserId;
    }

    public void setTaobaoUserId(String taobaoUserId) {
        this.taobaoUserId = taobaoUserId == null ? null : taobaoUserId.trim();
    }

    public String getBanlance() {
        return banlance;
    }

    public void setBanlance(String banlance) {
        this.banlance = banlance == null ? null : banlance.trim();
    }

    public String getGrantBalance() {
        return grantBalance;
    }

    public void setGrantBalance(String grantBalance) {
        this.grantBalance = grantBalance == null ? null : grantBalance.trim();
    }

    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance == null ? null : cashBalance.trim();
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance == null ? null : availableBalance.trim();
    }

    public String getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(String creditBalance) {
        this.creditBalance = creditBalance == null ? null : creditBalance.trim();
    }

    public String getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(String redPacket) {
        this.redPacket = redPacket == null ? null : redPacket.trim();
    }
}