package com.juzuan.advertiser.rpts.model;

public class CategoryRptsTotalGet {
    private Long id;

    private String taobaoUserId;

    private String catId;

    private String catName;

    private String ctr;

    private String ecpc;

    private String ecpm;

    private String cvr;

    private String roi;

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

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr == null ? null : ctr.trim();
    }

    public String getEcpc() {
        return ecpc;
    }

    public void setEcpc(String ecpc) {
        this.ecpc = ecpc == null ? null : ecpc.trim();
    }

    public String getEcpm() {
        return ecpm;
    }

    public void setEcpm(String ecpm) {
        this.ecpm = ecpm == null ? null : ecpm.trim();
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr == null ? null : cvr.trim();
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi == null ? null : roi.trim();
    }
}