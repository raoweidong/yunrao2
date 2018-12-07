package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class ZiDian {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Long conditonId;

    private String categoryName;

    private String codeName;

    private String codeValue;

    private String descName;

    private String descValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getConditonId() {
        return conditonId;
    }

    public void setConditonId(Long conditonId) {
        this.conditonId = conditonId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public String getDescName() {
        return descName;
    }

    public void setDescName(String descName) {
        this.descName = descName == null ? null : descName.trim();
    }

    public String getDescValue() {
        return descValue;
    }

    public void setDescValue(String descValue) {
        this.descValue = descValue == null ? null : descValue.trim();
    }
}