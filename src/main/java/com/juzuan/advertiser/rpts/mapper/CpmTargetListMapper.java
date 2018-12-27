package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CpmTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CpmTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CpmTargetList record);

    int deleteALL();

    int insertSelective(CpmTargetList record);

    CpmTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CpmTargetList record);

    int updateByPrimaryKey(CpmTargetList record);
}