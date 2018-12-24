package com.juzuan.advertiser.rpts.model;

public class ShopItemList {
    private Integer id;

    private String taobaoUserId;

    private Long itemId;

    private String itemName;

    private String itemLandingPage;

    private String itemPicUrl;

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemLandingPage() {
        return itemLandingPage;
    }

    public void setItemLandingPage(String itemLandingPage) {
        this.itemLandingPage = itemLandingPage == null ? null : itemLandingPage.trim();
    }

    public String getItemPicUrl() {
        return itemPicUrl;
    }

    public void setItemPicUrl(String itemPicUrl) {
        this.itemPicUrl = itemPicUrl == null ? null : itemPicUrl.trim();
    }
}