package com.juzuan.advertiser.rpts.model;

import java.util.ArrayList;

public class RelationshopLabel {
    private Integer id;

    private String taobaoUserId;

    private String behaviorValue;

    private String behaviorName;

    private String timeWindowValue;

    private String timeWindowName;

    public String getTimeWindowValue() {
        return timeWindowValue;
    }

    public void setTimeWindowValue(String timeWindowValue) {
        this.timeWindowValue = timeWindowValue == null ? null : timeWindowValue.trim();
    }

    public String getTimeWindowName() {
        return timeWindowName;
    }

    public void setTimeWindowName(String timeWindowName) {
        this.timeWindowName = timeWindowName == null ? null : timeWindowName.trim();
    }
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

    public String getBehaviorValue() {
        return behaviorValue;
    }

    public void setBehaviorValue(String behaviorValue) {
        this.behaviorValue = behaviorValue == null ? null : behaviorValue.trim();
    }

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName == null ? null : behaviorName.trim();
    }

}