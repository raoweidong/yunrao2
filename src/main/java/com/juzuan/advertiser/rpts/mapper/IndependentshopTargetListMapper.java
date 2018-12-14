package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.IndependentshopTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndependentshopTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IndependentshopTargetList record);

    int insertSelective(IndependentshopTargetList record);

    IndependentshopTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IndependentshopTargetList record);

    int updateByPrimaryKey(IndependentshopTargetList record);
}