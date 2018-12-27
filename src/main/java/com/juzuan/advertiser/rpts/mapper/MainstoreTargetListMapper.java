package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.MainstoreTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainstoreTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteALL();

    int insert(MainstoreTargetList record);

    int insertSelective(MainstoreTargetList record);

    MainstoreTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MainstoreTargetList record);

    int updateByPrimaryKey(MainstoreTargetList record);
}