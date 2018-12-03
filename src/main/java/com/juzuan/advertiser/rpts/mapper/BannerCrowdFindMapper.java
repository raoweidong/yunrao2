package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.BannerCrowdFind;

public interface BannerCrowdFindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BannerCrowdFind record);

    int insertSelective(BannerCrowdFind record);

    BannerCrowdFind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BannerCrowdFind record);

    int updateByPrimaryKey(BannerCrowdFind record);
}