package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdgroupRptsDayGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdgroupRptsDayGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdgroupRptsDayGet record);

    int insertSelective(AdgroupRptsDayGet record);

    AdgroupRptsDayGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdgroupRptsDayGet record);

    int updateByPrimaryKey(AdgroupRptsDayGet record);
}