package com.juzuan.advertiser.rpts.model;

public class AdzoneListBind {
    private Integer id;

    private String taobaoUserId;

    private Long campaignId;

    private Long adgroupId;

    private Long adzoneId;

    private String adzoneName;

    private String adzoneSizeList;

    private String allowAdFormatList;

    private Long allowAdvType;

    private Long mediaType;

    private Long adzoneLevel;

    private Long crowdId;

    private Long crowdType;

    private Long price;

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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public String getAdzoneName() {
        return adzoneName;
    }

    public void setAdzoneName(String adzoneName) {
        this.adzoneName = adzoneName == null ? null : adzoneName.trim();
    }

    public String getAdzoneSizeList() {
        return adzoneSizeList;
    }

    public void setAdzoneSizeList(String adzoneSizeList) {
        this.adzoneSizeList = adzoneSizeList == null ? null : adzoneSizeList.trim();
    }

    public String getAllowAdFormatList() {
        return allowAdFormatList;
    }

    public void setAllowAdFormatList(String allowAdFormatList) {
        this.allowAdFormatList = allowAdFormatList == null ? null : allowAdFormatList.trim();
    }

    public Long getAllowAdvType() {
        return allowAdvType;
    }

    public void setAllowAdvType(Long allowAdvType) {
        this.allowAdvType = allowAdvType;
    }

    public Long getMediaType() {
        return mediaType;
    }

    public void setMediaType(Long mediaType) {
        this.mediaType = mediaType;
    }

    public Long getAdzoneLevel() {
        return adzoneLevel;
    }

    public void setAdzoneLevel(Long adzoneLevel) {
        this.adzoneLevel = adzoneLevel;
    }

    public Long getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(Long crowdId) {
        this.crowdId = crowdId;
    }

    public Long getCrowdType() {
        return crowdType;
    }

    public void setCrowdType(Long crowdType) {
        this.crowdType = crowdType;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}