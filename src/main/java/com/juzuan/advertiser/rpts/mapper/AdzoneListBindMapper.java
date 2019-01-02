package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdzoneListBind;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdzoneListBindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdzoneListBind record);

    int deleteALL();

    int insertSelective(AdzoneListBind record);

    AdzoneListBind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdzoneListBind record);

    int updateByPrimaryKey(AdzoneListBind record);

    List<AdzoneListBind> selectAllAdzoneListBind();
}