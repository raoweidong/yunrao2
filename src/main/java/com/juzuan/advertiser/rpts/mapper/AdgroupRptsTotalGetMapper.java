package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdgroupRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdgroupRptsTotalGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdgroupRptsTotalGet record);

    int insertSelective(AdgroupRptsTotalGet record);

    AdgroupRptsTotalGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdgroupRptsTotalGet record);

    int updateByPrimaryKey(AdgroupRptsTotalGet record);
}