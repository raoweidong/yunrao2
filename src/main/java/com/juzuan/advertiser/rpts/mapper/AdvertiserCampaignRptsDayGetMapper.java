package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserCampaignRptsDayGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertiserCampaignRptsDayGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdvertiserCampaignRptsDayGet record);

    int insertSelective(AdvertiserCampaignRptsDayGet record);

    AdvertiserCampaignRptsDayGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdvertiserCampaignRptsDayGet record);

    int updateByPrimaryKey(AdvertiserCampaignRptsDayGet record);
}