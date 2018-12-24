package com.juzuan.advertiser.rpts.service.impl;

import com.juzuan.advertiser.rpts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynchronizeTablesServiceImpl implements SynchronizeTablesService {
    @Autowired
    private AdgroupListDetailsService adgroupListDetailsService;
    @Autowired
    private AdgroupListService adgroupListService;
    @Autowired
    private AdgroupRptsDayGetService adgroupRptsDayGetService;
    @Autowired
    private AdvertiserAccountRptsDayService advertiserAccountRptsDayService;
    @Autowired
    private AdvertiserAccountRptsTotalGetService advertiserAccountRptsTotalGetService;
    @Autowired
    private AdvertiserAdzoneRptsDayGetService advertiserAdzoneRptsDayGetService;
    @Autowired
    private AdvertiserAdzoneRptsTotalGetService advertiserAdzoneRptsTotalGetService;
    @Autowired
    private AdvertiserCampaignRptsDayGetService advertiserCampaignRptsDayGetService;
    @Autowired
    private AdvertiserCampaignRptsTotalGetService advertiserCampaignRptsTotalGetService;
    @Autowired
    private AdzoneListBindService adzoneListBindService;
    @Override
    public String synchronize(){
       adgroupListDetailsService.AdgroupListDetails();
       adgroupListService.AdgroupList();
       adgroupRptsDayGetService.AdgroupRptsDayGet();
       advertiserAccountRptsDayService.parseAndSaveAccountDays();
       advertiserAccountRptsTotalGetService.parseAndSaveAccountTotal();
       advertiserAdzoneRptsDayGetService.getAdzone();
       advertiserAdzoneRptsTotalGetService.parseAndSaveAdzoneTotal();
        advertiserCampaignRptsDayGetService.getAdvertiserCampaignRptsDay();
        advertiserCampaignRptsTotalGetService.parseCampaign();






        return "";
    }
}
