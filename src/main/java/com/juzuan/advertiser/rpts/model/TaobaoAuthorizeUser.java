package com.juzuan.advertiser.rpts.model;

import java.util.Date;

public class TaobaoAuthorizeUser {
    private Long id;

    private String taobaoUserId;

    private String taobaoOpenUid;

    private String taobaoUserNick;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private String reExpiresIn;

    private String expiresIn;

    private String r1ExpiresIn;

    private String r2ExpiresIn;

    private String w1ExpiresIn;

    private String w2ExpiresIn;

    private String r1Valid;

    private String r2Valid;

    private String w1Valid;

    private String w2Valid;

    private String refreshTokenValidTime;

    private String expireTime;

    private Date expireDate;

    private Byte taobaoAdminStatus;

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

    public String getTaobaoOpenUid() {
        return taobaoOpenUid;
    }

    public void setTaobaoOpenUid(String taobaoOpenUid) {
        this.taobaoOpenUid = taobaoOpenUid == null ? null : taobaoOpenUid.trim();
    }

    public String getTaobaoUserNick() {
        return taobaoUserNick;
    }

    public void setTaobaoUserNick(String taobaoUserNick) {
        this.taobaoUserNick = taobaoUserNick == null ? null : taobaoUserNick.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType == null ? null : tokenType.trim();
    }

    public String getReExpiresIn() {
        return reExpiresIn;
    }

    public void setReExpiresIn(String reExpiresIn) {
        this.reExpiresIn = reExpiresIn == null ? null : reExpiresIn.trim();
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn == null ? null : expiresIn.trim();
    }

    public String getR1ExpiresIn() {
        return r1ExpiresIn;
    }

    public void setR1ExpiresIn(String r1ExpiresIn) {
        this.r1ExpiresIn = r1ExpiresIn == null ? null : r1ExpiresIn.trim();
    }

    public String getR2ExpiresIn() {
        return r2ExpiresIn;
    }

    public void setR2ExpiresIn(String r2ExpiresIn) {
        this.r2ExpiresIn = r2ExpiresIn == null ? null : r2ExpiresIn.trim();
    }

    public String getW1ExpiresIn() {
        return w1ExpiresIn;
    }

    public void setW1ExpiresIn(String w1ExpiresIn) {
        this.w1ExpiresIn = w1ExpiresIn == null ? null : w1ExpiresIn.trim();
    }

    public String getW2ExpiresIn() {
        return w2ExpiresIn;
    }

    public void setW2ExpiresIn(String w2ExpiresIn) {
        this.w2ExpiresIn = w2ExpiresIn == null ? null : w2ExpiresIn.trim();
    }

    public String getR1Valid() {
        return r1Valid;
    }

    public void setR1Valid(String r1Valid) {
        this.r1Valid = r1Valid == null ? null : r1Valid.trim();
    }

    public String getR2Valid() {
        return r2Valid;
    }

    public void setR2Valid(String r2Valid) {
        this.r2Valid = r2Valid == null ? null : r2Valid.trim();
    }

    public String getW1Valid() {
        return w1Valid;
    }

    public void setW1Valid(String w1Valid) {
        this.w1Valid = w1Valid == null ? null : w1Valid.trim();
    }

    public String getW2Valid() {
        return w2Valid;
    }

    public void setW2Valid(String w2Valid) {
        this.w2Valid = w2Valid == null ? null : w2Valid.trim();
    }

    public String getRefreshTokenValidTime() {
        return refreshTokenValidTime;
    }

    public void setRefreshTokenValidTime(String refreshTokenValidTime) {
        this.refreshTokenValidTime = refreshTokenValidTime == null ? null : refreshTokenValidTime.trim();
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime == null ? null : expireTime.trim();
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Byte getTaobaoAdminStatus() {
        return taobaoAdminStatus;
    }

    public void setTaobaoAdminStatus(Byte taobaoAdminStatus) {
        this.taobaoAdminStatus = taobaoAdminStatus;
    }
}