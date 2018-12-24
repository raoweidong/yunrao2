package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.RelationshopList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationshopListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RelationshopList record);

    int insertSelective(RelationshopList record);

    RelationshopList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RelationshopList record);

    int updateByPrimaryKey(RelationshopList record);
}