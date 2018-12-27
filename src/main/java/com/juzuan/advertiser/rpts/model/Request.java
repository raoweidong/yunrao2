package com.juzuan.advertiser.rpts.model;

public class Request {
    private Integer id;

    private Long effect;

    private Long campaignModel;

    private String effectType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getEffect() {
        return effect;
    }

    public void setEffect(Long effect) {
        this.effect = effect;
    }

    public Long getCampaignModel() {
        return campaignModel;
    }

    public void setCampaignModel(Long campaignModel) {
        this.campaignModel = campaignModel;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType == null ? null : effectType.trim();
    }
}