package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdzoneList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdzoneListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdzoneList record);

    int insertSelective(AdzoneList record);

    AdzoneList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdzoneList record);

    int updateByPrimaryKey(AdzoneList record);
    List<AdzoneList> selectAllAdzoneList();

}