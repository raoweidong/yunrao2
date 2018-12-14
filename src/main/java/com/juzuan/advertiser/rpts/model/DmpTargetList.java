package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class DmpTargetList {
    private Integer id;

    private String taobaoUserId;

    private Long coverage;

    private Date enableTime;

    private String dmpCrowdName;

    private Long dmpCrowdId;

    private Date updateTime;

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

    public Long getCoverage() {
        return coverage;
    }

    public void setCoverage(Long coverage) {
        this.coverage = coverage;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public String getDmpCrowdName() {
        return dmpCrowdName;
    }

    public void setDmpCrowdName(String dmpCrowdName) {
        this.dmpCrowdName = dmpCrowdName == null ? null : dmpCrowdName.trim();
    }

    public Long getDmpCrowdId() {
        return dmpCrowdId;
    }

    public void setDmpCrowdId(Long dmpCrowdId) {
        this.dmpCrowdId = dmpCrowdId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}