package com.juzuan.advertiser.rpts.model;

public class IndustryshopList {
    private Integer id;

    private String taobaoUserId;

    private String industryShopId;

    private String industryShopName;

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

    public String getIndustryShopId() {
        return industryShopId;
    }

    public void setIndustryShopId(String industryShopId) {
        this.industryShopId = industryShopId == null ? null : industryShopId.trim();
    }

    public String getIndustryShopName() {
        return industryShopName;
    }

    public void setIndustryShopName(String industryShopName) {
        this.industryShopName = industryShopName == null ? null : industryShopName.trim();
    }
}