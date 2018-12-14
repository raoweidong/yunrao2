package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.SeniorinterestList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SeniorinterestListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SeniorinterestList record);

    int insertSelective(SeniorinterestList record);

    SeniorinterestList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SeniorinterestList record);

    int updateByPrimaryKey(SeniorinterestList record);
}