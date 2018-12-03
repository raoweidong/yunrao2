package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.RelationshopList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationshopListMapper {
    int deleteByPrimaryKey(String packageId);

    int insert(RelationshopList record);

    int insertSelective(RelationshopList record);

    RelationshopList selectByPrimaryKey(String packageId);

    int updateByPrimaryKeySelective(RelationshopList record);

    int updateByPrimaryKey(RelationshopList record);
}