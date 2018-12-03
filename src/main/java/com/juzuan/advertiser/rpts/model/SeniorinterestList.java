package com.juzuan.advertiser.rpts.model;

public class SeniorinterestList {
    private String catId;

    private String catName;

    private String interestId;

    private String interestName;

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

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId == null ? null : interestId.trim();
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName == null ? null : interestName.trim();
    }
}