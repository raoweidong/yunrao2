package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsDayGet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertiserAdzoneRptsDayGetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdvertiserAdzoneRptsDayGet record);

    int insertSelective(AdvertiserAdzoneRptsDayGet record);

    AdvertiserAdzoneRptsDayGet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdvertiserAdzoneRptsDayGet record);

    int updateByPrimaryKey(AdvertiserAdzoneRptsDayGet record);
    List<AdvertiserAdzoneRptsDayGet> queryAdzoneRptsDayGetsBySql(Map<String,Object> data);
}