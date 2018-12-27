package com.juzuan.advertiser.rpts.service;

import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.Response;

public interface CampaignListService {

    String campaignList();
    int insert(CampaignList record);
    Response requestCampaignList(String userId);




}
