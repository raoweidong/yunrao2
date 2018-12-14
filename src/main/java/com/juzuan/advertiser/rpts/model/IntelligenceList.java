package com.juzuan.advertiser.rpts.model;

public class IntelligenceList {
    private Long campaignType;

    private String scaleDesc;

    private String scaleLevel;

    public Long getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(Long campaignType) {
        this.campaignType = campaignType;
    }

    public String getScaleDesc() {
        return scaleDesc;
    }

    public void setScaleDesc(String scaleDesc) {
        this.scaleDesc = scaleDesc == null ? null : scaleDesc.trim();
    }

    public String getScaleLevel() {
        return scaleLevel;
    }

    public void setScaleLevel(String scaleLevel) {
        this.scaleLevel = scaleLevel == null ? null : scaleLevel.trim();
    }
}