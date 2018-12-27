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
    @Autowired
    private AdzoneListService adzoneListService;
    @Autowired
    private CampaignDetailsService campaignDetailsService;
    @Autowired
    private CampaignListService campaignListService;
    @Autowired
    private CreativeListBindService creativeListBindService;
    @Autowired
    private CreativeListService creativeListService;
    @Autowired
    private CreativeRptsDaygetService creativeRptsDaygetService;
    @Autowired
    private CrowdListService crowdListService;
    @Autowired
    private RelationshopListService relationshopListService;
    @Autowired
    private SeniorinterestListService seniorinterestListService;
    @Autowired
    private TargetCatelabelListService targetCatelabelListService;
    @Autowired
    private TargetCatListService targetCatListService;
    @Autowired
    private TargetRptsDayGetService targetRptsDayGetService;
    @Override
    public String synchronize(String userId){
        campaignListService.requestCampaignList(userId);//计划列表  只需sessionKey
        //campaignDetailsService.getCampaignDetail();//计划详细信息列表 计划id
        advertiserCampaignRptsDayGetService.requestAdvertiserCampaignRptsDay(userId);//计划分日列表数据 计划id 效果类型之类的
       /* advertiserCampaignRptsTotalGetService.parseCampaign();//钻展计划多日数据汇总查询 只需sessionKey
        adgroupListService.AdgroupList();//单元列表 计划id
        adgroupListDetailsService.AdgroupListDetails(userId);//单元详细信息列表 单元id
       adgroupRptsDayGetService.AdgroupRptsDayGet();//单元分日列表数据 计划id和计划id
       advertiserAccountRptsDayService.parseAndSaveAccountDays();//钻展广告主账户数据分日列表查询  只需sessionKey
       advertiserAccountRptsTotalGetService.parseAndSaveAccountTotal();//钻展广告主账户多日数据汇总查询 只需sessionKey
        adzoneListService.getAdzoneList();//资源位列表 只需sessionKey
        adzoneListBindService.getBannerAdgroupAdzone();//已绑定资源位列表  计划和单元id
       advertiserAdzoneRptsDayGetService.getAdzone();//资源位分日列表数据 计划\单元\资源位 id
       advertiserAdzoneRptsTotalGetService.parseAndSaveAdzoneTotal();//资源位多日汇总列表数据 点击效果\计划类型\分页之类的
        creativeListService.CreativeList();//创意列表  只需sessionKey
        creativeListBindService.CreativeListBind();//获取已绑定的创意  计划和单元id;
        creativeRptsDaygetService.creativeRptsDayget();//创意分日列表数据 计划\单元\创意,id
        crowdListService.CrowdList();//全店计划定向人群列表  需要计划id和单元id
        relationshopListService.Relationshop();//店铺型定向店铺包列表 计划类型
        seniorinterestListService.SeniorinterestList();//类目型定向高级兴趣点 计划类型
        targetCatelabelListService.TargetCatelabeList();//店铺型定向-本店行为细分标签列表  只需sessionKey
        targetCatListService.TargetCatList();//高级兴趣点  计划类型
       targetRptsDayGetService.TargetRptsDayGet();//定向分日列表数据  计划id 单元id 定向id*/

        return "所有初始数据同步完成";
    }
}
