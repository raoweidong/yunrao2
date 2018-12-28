package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsDayGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertiserAccountRptsDayGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserAccountRptsDayGet record);

    int insertSelective(AdvertiserAccountRptsDayGet record);

    AdvertiserAccountRptsDayGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvertiserAccountRptsDayGet record);

    int updateByPrimaryKey(AdvertiserAccountRptsDayGet record);
}