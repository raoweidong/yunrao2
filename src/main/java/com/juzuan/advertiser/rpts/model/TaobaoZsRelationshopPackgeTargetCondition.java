package com.juzuan.advertiser.rpts.model;

public class TaobaoZsRelationshopPackgeTargetCondition {
    private Integer id;

    private String taobaoUserId;

    private Long maxPerSale;

    private Long minPerSale;

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

    public Long getMaxPerSale() {
        return maxPerSale;
    }

    public void setMaxPerSale(Long maxPerSale) {
        this.maxPerSale = maxPerSale;
    }

    public Long getMinPerSale() {
        return minPerSale;
    }

    public void setMinPerSale(Long minPerSale) {
        this.minPerSale = minPerSale;
    }
}