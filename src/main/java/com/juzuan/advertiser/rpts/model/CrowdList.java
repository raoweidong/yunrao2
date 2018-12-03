package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class CrowdList {
    private Integer id;

    private String taobaoUserId;

    private Long adgroupId;

    private Long campaignId;

    private Long targetId;

    private String crowdName;

    private Long crowdType;

    private String crowdValue;

    private Date gmtCreate;

    private Date gmtModified;

    private Long adzoneId;

    private String subCrowdName;

    private String subCrowdValue;

    private Long price;

    private String catIdList;

    private String shopScaleIdList;

    private String minPerSale;

    private String maxPerSale;

    private String shopPreferenceValue;

    private Long catId;

    private String catName;

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

    public Long getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(Long adgroupId) {
        this.adgroupId = adgroupId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName == null ? null : crowdName.trim();
    }

    public Long getCrowdType() {
        return crowdType;
    }

    public void setCrowdType(Long crowdType) {
        this.crowdType = crowdType;
    }

    public String getCrowdValue() {
        return crowdValue;
    }

    public void setCrowdValue(String crowdValue) {
        this.crowdValue = crowdValue == null ? null : crowdValue.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public String getSubCrowdName() {
        return subCrowdName;
    }

    public void setSubCrowdName(String subCrowdName) {
        this.subCrowdName = subCrowdName == null ? null : subCrowdName.trim();
    }

    public String getSubCrowdValue() {
        return subCrowdValue;
    }

    public void setSubCrowdValue(String subCrowdValue) {
        this.subCrowdValue = subCrowdValue == null ? null : subCrowdValue.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCatIdList() {
        return catIdList;
    }

    public void setCatIdList(String catIdList) {
        this.catIdList = catIdList == null ? null : catIdList.trim();
    }

    public String getShopScaleIdList() {
        return shopScaleIdList;
    }

    public void setShopScaleIdList(String shopScaleIdList) {
        this.shopScaleIdList = shopScaleIdList == null ? null : shopScaleIdList.trim();
    }

    public String getMinPerSale() {
        return minPerSale;
    }

    public void setMinPerSale(String minPerSale) {
        this.minPerSale = minPerSale == null ? null : minPerSale.trim();
    }

    public String getMaxPerSale() {
        return maxPerSale;
    }

    public void setMaxPerSale(String maxPerSale) {
        this.maxPerSale = maxPerSale == null ? null : maxPerSale.trim();
    }

    public String getShopPreferenceValue() {
        return shopPreferenceValue;
    }

    public void setShopPreferenceValue(String shopPreferenceValue) {
        this.shopPreferenceValue = shopPreferenceValue == null ? null : shopPreferenceValue.trim();
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }
}