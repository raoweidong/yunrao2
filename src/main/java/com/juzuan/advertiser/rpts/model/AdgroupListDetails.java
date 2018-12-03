package com.juzuan.advertiser.rpts.model;

public class AdgroupListDetails {
    private Long id;

    private String taobaoUserId;

    private Long campaignId;

    private Long adgroupId;

    private String adgroupName;

    private Long onlineStatus;

    private Integer intelligentBid;

    private Integer adboardFilter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAdgroupName() {
        return adgroupName;
    }

    public void setAdgroupName(String adgroupName) {
        this.adgroupName = adgroupName == null ? null : adgroupName.trim();
    }

    public Long getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Long onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getIntelligentBid() {
        return intelligentBid;
    }

    public void setIntelligentBid(Integer intelligentBid) {
        this.intelligentBid = intelligentBid;
    }

    public Integer getAdboardFilter() {
        return adboardFilter;
    }

    public void setAdboardFilter(Integer adboardFilter) {
        this.adboardFilter = adboardFilter;
    }
}