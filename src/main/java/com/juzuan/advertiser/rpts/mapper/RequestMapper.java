package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.Request;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RequestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Request record);

    int insertSelective(Request record);

    Request selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Request record);

    int updateByPrimaryKey(Request record);

    List<Request> selectAllRequest();
}