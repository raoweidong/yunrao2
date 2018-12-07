package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.ZiDian;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZiDianMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ZiDian record);

    int insertSelective(ZiDian record);

    ZiDian selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ZiDian record);

    int updateByPrimaryKey(ZiDian record);
}