package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CreativeListBind;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CreativeListBindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreativeListBind record);

    int insertSelective(CreativeListBind record);

    CreativeListBind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreativeListBind record);

    int updateByPrimaryKey(CreativeListBind record);
}