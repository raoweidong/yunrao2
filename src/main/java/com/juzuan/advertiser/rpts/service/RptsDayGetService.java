package com.juzuan.advertiser.rpts.service;

import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsDayGet;

import java.util.List;

public interface RptsDayGetService {
    String rpts();

    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserAccountRptsDayGet record);

    int insertSelective(AdvertiserAccountRptsDayGet record);

    AdvertiserAccountRptsDayGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvertiserAccountRptsDayGet record);

    int updateByPrimaryKey(AdvertiserAccountRptsDayGet record);
    List<AdvertiserAccountRptsDayGet> selectAll();

}
