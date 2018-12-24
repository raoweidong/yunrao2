package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.RelationshopLabel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelationshopLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RelationshopLabel record);

    int insertSelective(RelationshopLabel record);

    RelationshopLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RelationshopLabel record);

    int updateByPrimaryKey(RelationshopLabel record);
}