package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TargetAdzoneRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetAdzoneRptsTotalGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TargetAdzoneRptsTotalGet record);

    int insertSelective(TargetAdzoneRptsTotalGet record);

    TargetAdzoneRptsTotalGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TargetAdzoneRptsTotalGet record);

    int updateByPrimaryKey(TargetAdzoneRptsTotalGet record);
}