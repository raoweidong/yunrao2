package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TargetRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetRptsTotalGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TargetRptsTotalGet record);

    int insertSelective(TargetRptsTotalGet record);

    TargetRptsTotalGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TargetRptsTotalGet record);

    int updateByPrimaryKey(TargetRptsTotalGet record);
}