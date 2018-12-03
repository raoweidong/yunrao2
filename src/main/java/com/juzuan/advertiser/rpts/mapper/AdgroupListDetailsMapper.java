package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdgroupListDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdgroupListDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdgroupListDetails record);

    int insertSelective(AdgroupListDetails record);

    AdgroupListDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdgroupListDetails record);

    int updateByPrimaryKey(AdgroupListDetails record);
}