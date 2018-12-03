package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class AdzoneList {
    private Integer id;

    private String taobaoUserId;

    private Long adzoneId;

    private String adzoneName;

    private String adzoneSizeList;

    private String allowAdFormatList;

    private Long allowAdvType;

    private Long adzoneLevel;

    private Long mediaType;

    private Long tpv;

    private String ctr;

    private Date firstTime;

    private Long rcmdScore;

    private Long bidScore;

    private Long cpmScore;

    private Long cpcScore;

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

    public Long getAdzoneLevel() {
        return adzoneLevel;
    }

    public void setAdzoneLevel(Long adzoneLevel) {
        this.adzoneLevel = adzoneLevel;
    }

    public Long getMediaType() {
        return mediaType;
    }

    public void setMediaType(Long mediaType) {
        this.mediaType = mediaType;
    }

    public Long getTpv() {
        return tpv;
    }

    public void setTpv(Long tpv) {
        this.tpv = tpv;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr == null ? null : ctr.trim();
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Long getRcmdScore() {
        return rcmdScore;
    }

    public void setRcmdScore(Long rcmdScore) {
        this.rcmdScore = rcmdScore;
    }

    public Long getBidScore() {
        return bidScore;
    }

    public void setBidScore(Long bidScore) {
        this.bidScore = bidScore;
    }

    public Long getCpmScore() {
        return cpmScore;
    }

    public void setCpmScore(Long cpmScore) {
        this.cpmScore = cpmScore;
    }

    public Long getCpcScore() {
        return cpcScore;
    }

    public void setCpcScore(Long cpcScore) {
        this.cpcScore = cpcScore;
    }
}