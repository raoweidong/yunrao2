package com.juzuan.advertiser.rpts.query;

import java.util.Date;

public class DayGetQuery {

    private String  userId;

    private Date logDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
}
