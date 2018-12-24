package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AccountGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountGetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountGet record);

    int insertSelective(AccountGet record);

    AccountGet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountGet record);

    int updateByPrimaryKey(AccountGet record);
}