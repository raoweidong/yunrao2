package com.juzuan.advertiser.rpts.model;

public class TargetCatList {
    private Integer id;

    private String catId;

    private String catName;

    private String optionValue;

    private String optionName;

    private Long campaignType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId == null ? null : catId.trim();
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue == null ? null : optionValue.trim();
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }

    public Long getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(Long campaignType) {
        this.campaignType = campaignType;
    }
}