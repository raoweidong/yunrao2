package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CategoryRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryRptsTotalGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CategoryRptsTotalGet record);

    int insertSelective(CategoryRptsTotalGet record);

    CategoryRptsTotalGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CategoryRptsTotalGet record);

    int updateByPrimaryKey(CategoryRptsTotalGet record);


}