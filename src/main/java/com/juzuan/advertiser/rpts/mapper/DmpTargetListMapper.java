package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.DmpTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DmpTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmpTargetList record);

    int deleteALL();

    int insertSelective(DmpTargetList record);

    DmpTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmpTargetList record);

    int updateByPrimaryKey(DmpTargetList record);
}