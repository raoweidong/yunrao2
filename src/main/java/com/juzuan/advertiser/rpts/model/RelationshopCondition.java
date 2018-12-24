package com.juzuan.advertiser.rpts.model;

public class RelationshopCondition {
    private Integer id;

    private String taobaoUserId;

    private String cateId;

    private String cateName;

    private String shopScaleId;

    private String shopScaleName;

    private Long maxPerSale;

    private Long minPerSale;

    private String shopPreferenceValue;

    private String shopPreferenceName;

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

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId == null ? null : cateId.trim();
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName == null ? null : cateName.trim();
    }

    public String getShopScaleId() {
        return shopScaleId;
    }

    public void setShopScaleId(String shopScaleId) {
        this.shopScaleId = shopScaleId == null ? null : shopScaleId.trim();
    }

    public String getShopScaleName() {
        return shopScaleName;
    }

    public void setShopScaleName(String shopScaleName) {
        this.shopScaleName = shopScaleName == null ? null : shopScaleName.trim();
    }

    public Long getMaxPerSale() {
        return maxPerSale;
    }

    public void setMaxPerSale(Long maxPerSale) {
        this.maxPerSale = maxPerSale;
    }

    public Long getMinPerSale() {
        return minPerSale;
    }

    public void setMinPerSale(Long minPerSale) {
        this.minPerSale = minPerSale;
    }

    public String getShopPreferenceValue() {
        return shopPreferenceValue;
    }

    public void setShopPreferenceValue(String shopPreferenceValue) {
        this.shopPreferenceValue = shopPreferenceValue == null ? null : shopPreferenceValue.trim();
    }

    public String getShopPreferenceName() {
        return shopPreferenceName;
    }

    public void setShopPreferenceName(String shopPreferenceName) {
        this.shopPreferenceName = shopPreferenceName == null ? null : shopPreferenceName.trim();
    }
}