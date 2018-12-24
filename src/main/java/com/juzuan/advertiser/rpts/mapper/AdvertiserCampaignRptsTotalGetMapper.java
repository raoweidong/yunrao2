package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserCampaignRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertiserCampaignRptsTotalGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserCampaignRptsTotalGet record);

    int insertSelective(AdvertiserCampaignRptsTotalGet record);

    AdvertiserCampaignRptsTotalGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvertiserCampaignRptsTotalGet record);

    int updateByPrimaryKey(AdvertiserCampaignRptsTotalGet record);
}