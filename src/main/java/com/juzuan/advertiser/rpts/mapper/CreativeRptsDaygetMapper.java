package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CreativeRptsDayget;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreativeRptsDaygetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreativeRptsDayget record);

    int insertSelective(CreativeRptsDayget record);

    CreativeRptsDayget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreativeRptsDayget record);

    int updateByPrimaryKey(CreativeRptsDayget record);
}