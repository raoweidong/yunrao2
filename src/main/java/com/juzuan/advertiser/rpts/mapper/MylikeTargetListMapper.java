package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.MylikeTargetList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MylikeTargetListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MylikeTargetList record);

    int insertSelective(MylikeTargetList record);

    MylikeTargetList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MylikeTargetList record);

    int updateByPrimaryKey(MylikeTargetList record);
}