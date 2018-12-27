package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertiserAdzoneRptsTotalGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserAdzoneRptsTotalGet record);

    int insertSelective(AdvertiserAdzoneRptsTotalGet record);

    AdvertiserAdzoneRptsTotalGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvertiserAdzoneRptsTotalGet record);

    int updateByPrimaryKey(AdvertiserAdzoneRptsTotalGet record);
}