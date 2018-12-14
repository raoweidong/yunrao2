package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaobaoAuthorizeUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaobaoAuthorizeUser record);

    int insertSelective(TaobaoAuthorizeUser record);

    TaobaoAuthorizeUser slectByUserId(String useId);

    TaobaoAuthorizeUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaobaoAuthorizeUser record);

    int updateByPrimaryKey(TaobaoAuthorizeUser record);

    List<TaobaoAuthorizeUser> selectAllToken();
}