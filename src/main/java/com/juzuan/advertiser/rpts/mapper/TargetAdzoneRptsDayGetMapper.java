package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TargetAdzoneRptsDayGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetAdzoneRptsDayGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TargetAdzoneRptsDayGet record);

    int insertSelective(TargetAdzoneRptsDayGet record);

    TargetAdzoneRptsDayGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TargetAdzoneRptsDayGet record);

    int updateByPrimaryKey(TargetAdzoneRptsDayGet record);
}