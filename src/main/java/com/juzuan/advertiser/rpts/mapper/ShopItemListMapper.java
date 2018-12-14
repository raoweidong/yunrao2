package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.ShopItemList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopItemListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopItemList record);

    int insertSelective(ShopItemList record);

    ShopItemList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopItemList record);

    int updateByPrimaryKey(ShopItemList record);
}