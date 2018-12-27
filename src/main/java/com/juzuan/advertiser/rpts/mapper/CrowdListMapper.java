package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CrowdList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrowdListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrowdList record);

    int deleteALL();

    int insertSelective(CrowdList record);

    CrowdList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrowdList record);

    int updateByPrimaryKey(CrowdList record);
    List<CrowdList> selectAllCrowd();
}