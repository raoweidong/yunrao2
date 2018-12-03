package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CreativeList;

public interface CreativeListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreativeList record);

    int insertSelective(CreativeList record);

    CreativeList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreativeList record);

    int updateByPrimaryKey(CreativeList record);
}