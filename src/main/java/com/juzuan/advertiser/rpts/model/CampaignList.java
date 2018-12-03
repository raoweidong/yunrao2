package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class CampaignList {
    private Long id;

    private String taobaoUserId;

    private Long campaignId;

    private String campaignName;

    private Integer campaignType;

    private Date startTime;

    private Date endTime;

    private Double dayBudget;

    private Integer onlineStatus;

    private Integer speedType;

    private String workdays;

    private String weekEnds;

    private String lifeCycle;

    private String marketingdemand;

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

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName == null ? null : campaignName.trim();
    }

    public Integer getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(Integer campaignType) {
        this.campaignType = campaignType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getDayBudget() {
        return dayBudget;
    }

    public void setDayBudget(Double dayBudget) {
        this.dayBudget = dayBudget;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getSpeedType() {
        return speedType;
    }

    public void setSpeedType(Integer speedType) {
        this.speedType = speedType;
    }

    public String getWorkdays() {
        return workdays;
    }

    public void setWorkdays(String workdays) {
        this.workdays = workdays == null ? null : workdays.trim();
    }

    public String getWeekEnds() {
        return weekEnds;
    }

    public void setWeekEnds(String weekEnds) {
        this.weekEnds = weekEnds == null ? null : weekEnds.trim();
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle == null ? null : lifeCycle.trim();
    }

    public String getMarketingdemand() {
        return marketingdemand;
    }

    public void setMarketingdemand(String marketingdemand) {
        this.marketingdemand = marketingdemand == null ? null : marketingdemand.trim();
    }
}