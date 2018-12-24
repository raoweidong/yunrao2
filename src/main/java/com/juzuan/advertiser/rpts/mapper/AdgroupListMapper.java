package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdgroupList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AdgroupListMapper {
    int deleteByPrimaryKey(Long id);

    int deleteBySource(Integer adgroupSource);

    int insert(AdgroupList record);

    int insertSelective(AdgroupList record);

    AdgroupList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdgroupList record);

    int updateByPrimaryKey(AdgroupList record);

    List<AdgroupList> selectAllAdgroup();
}