package com.juzuan.advertiser.rpts.model;

public class Response {
    private int code;//0 成功 ；1 失败
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
