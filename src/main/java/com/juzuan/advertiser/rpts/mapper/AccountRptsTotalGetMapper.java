package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AccountRptsTotalGet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRptsTotalGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountRptsTotalGet record);

    int insertSelective(AccountRptsTotalGet record);

    AccountRptsTotalGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountRptsTotalGet record);

    int updateByPrimaryKey(AccountRptsTotalGet record);
}