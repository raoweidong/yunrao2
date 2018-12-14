package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.RelationshopCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationshopConditionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RelationshopCondition record);

    int insertSelective(RelationshopCondition record);

    RelationshopCondition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RelationshopCondition record);

    int updateByPrimaryKey(RelationshopCondition record);

    List<RelationshopCondition> selectDistinct();
}