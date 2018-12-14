package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.SimlikeTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimlikeTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SimlikeTargetList record);

    int insertSelective(SimlikeTargetList record);

    SimlikeTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SimlikeTargetList record);

    int updateByPrimaryKey(SimlikeTargetList record);
}