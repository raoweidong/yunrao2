package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TargetCatelabelList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TargetCatelabelListMapper {
    int deleteByPrimaryKey(Integer cateId);

    int insert(TargetCatelabelList record);

    int insertSelective(TargetCatelabelList record);

    TargetCatelabelList selectByPrimaryKey(Integer cateId);

    int updateByPrimaryKeySelective(TargetCatelabelList record);

    int updateByPrimaryKey(TargetCatelabelList record);
}