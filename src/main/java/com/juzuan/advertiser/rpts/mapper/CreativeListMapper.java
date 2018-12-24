package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CreativeList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreativeListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreativeList record);

    int insertSelective(CreativeList record);

    CreativeList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreativeList record);

    int updateByPrimaryKey(CreativeList record);
}