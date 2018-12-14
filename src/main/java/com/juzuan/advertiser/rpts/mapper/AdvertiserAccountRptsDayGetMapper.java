package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsDayGet;
import com.juzuan.advertiser.rpts.query.DayGetQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdvertiserAccountRptsDayGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserAccountRptsDayGet record);

    int insertSelective(AdvertiserAccountRptsDayGet record);

    AdvertiserAccountRptsDayGet selectByPrimaryKey(Long id);

    List<AdvertiserAccountRptsDayGet> selectByCartNum(Long  cartNum);

    List<AdvertiserAccountRptsDayGet> selectAll();

    AdvertiserAccountRptsDayGet selectByUserIdAndLogDate(DayGetQuery query);

    int updateByPrimaryKeySelective(AdvertiserAccountRptsDayGet record);

    int updateByPrimaryKey(AdvertiserAccountRptsDayGet record);
}