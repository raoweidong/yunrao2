package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.IntelligenceList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IntelligenceListMapper {
    int deleteByPrimaryKey(Long campaignType);

    int insert(IntelligenceList record);

    int insertSelective(IntelligenceList record);

    IntelligenceList selectByPrimaryKey(Long campaignType);

    int updateByPrimaryKeySelective(IntelligenceList record);

    int updateByPrimaryKey(IntelligenceList record);
}