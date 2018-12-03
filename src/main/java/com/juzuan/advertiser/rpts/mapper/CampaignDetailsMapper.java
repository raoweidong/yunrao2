package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CampaignDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CampaignDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CampaignDetails record);

    int insertSelective(CampaignDetails record);

    CampaignDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CampaignDetails record);

    int updateByPrimaryKey(CampaignDetails record);
}