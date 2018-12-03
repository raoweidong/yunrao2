package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TargetRptsDayGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetRptsDayGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TargetRptsDayGet record);

    int insertSelective(TargetRptsDayGet record);

    TargetRptsDayGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TargetRptsDayGet record);

    int updateByPrimaryKey(TargetRptsDayGet record);
}