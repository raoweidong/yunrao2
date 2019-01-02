package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertiserAccountRptsTotalGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserAccountRptsTotalGet record);

    int insertSelective(AdvertiserAccountRptsTotalGet record);

    AdvertiserAccountRptsTotalGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvertiserAccountRptsTotalGet record);

    int updateByPrimaryKey(AdvertiserAccountRptsTotalGet record);
}