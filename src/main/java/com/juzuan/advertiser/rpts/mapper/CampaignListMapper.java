package com.juzuan.advertiser.rpts.mapper;

import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.query.UserAndId;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CampaignListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CampaignList record);

    int insertSelective(CampaignList record);

    CampaignList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CampaignList record);

    int updateByPrimaryKey(CampaignList record);

    List<CampaignList> selectAllCampaign();
    List<CampaignList> selectDistinct();
    CampaignList selectByUserAndId(UserAndId twoCanShu);
    void deleteAll();
    List<CampaignList> selectByUserId(String userId);


}