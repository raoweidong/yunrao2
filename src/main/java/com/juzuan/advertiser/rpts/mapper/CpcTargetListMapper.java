package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CpcTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CpcTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CpcTargetList record);

    int insertSelective(CpcTargetList record);

    CpcTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CpcTargetList record);

    int updateByPrimaryKey(CpcTargetList record);
}