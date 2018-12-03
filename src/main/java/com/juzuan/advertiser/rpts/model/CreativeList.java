package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class CreativeList {
    private Integer id;

    private String taobaoUserId;

    private Long creativeId;

    private String creativeName;

    private Integer auditStatus;

    private String catId;

    private String catName;

    private String clickUrl;

    private Integer creativeLevel;

    private String creativeSize;

    private String expireTime;

    private Integer format;

    private Date createTime;

    private Date modifiedTime;

    private String imagePath;

    private Integer packageType;

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

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName == null ? null : creativeName.trim();
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl == null ? null : clickUrl.trim();
    }

    public Integer getCreativeLevel() {
        return creativeLevel;
    }

    public void setCreativeLevel(Integer creativeLevel) {
        this.creativeLevel = creativeLevel;
    }

    public String getCreativeSize() {
        return creativeSize;
    }

    public void setCreativeSize(String creativeSize) {
        this.creativeSize = creativeSize == null ? null : creativeSize.trim();
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime == null ? null : expireTime.trim();
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }
}