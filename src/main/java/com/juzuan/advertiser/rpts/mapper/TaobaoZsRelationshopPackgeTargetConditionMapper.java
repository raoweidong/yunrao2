package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TaobaoZsRelationshopPackgeTargetCondition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaobaoZsRelationshopPackgeTargetConditionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaobaoZsRelationshopPackgeTargetCondition record);

    int insertSelective(TaobaoZsRelationshopPackgeTargetCondition record);

    TaobaoZsRelationshopPackgeTargetCondition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaobaoZsRelationshopPackgeTargetCondition record);

    int updateByPrimaryKey(TaobaoZsRelationshopPackgeTargetCondition record);

    TaobaoZsRelationshopPackgeTargetCondition selectByUserId(String userId);
}