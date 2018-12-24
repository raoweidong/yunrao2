package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignDetailsMapper;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CampaignDetails;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CampaignDetailsService;
import com.juzuan.advertiser.rpts.service.CampaignListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCampaignGetRequest;
import com.taobao.api.response.ZuanshiBannerCampaignGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计划详情列表
 */
@Service
public class CampaignDetailsServiceImpl implements CampaignDetailsService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignDetailsMapper campaignDetailsMapper;
    //定时更新：每天2:05
    //@Scheduled(cron = "0 5 2 * * ? ")
    public String getCampaignDetail(){
        campaignDetailsMapper.deleteBySource(0L);
        List<CampaignList> campaignLists=campaignListMapper.selectAllCampaign();
        for (CampaignList campaignList:campaignLists){
            String UserId=campaignList.getTaobaoUserId();
            TaobaoAuthorizeUser taobaoAuthorizeUser =taobaoAuthorizeUserMapper.slectByUserId(UserId);
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            Long id=campaignList.getCampaignId();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerCampaignGetRequest req = new ZuanshiBannerCampaignGetRequest();
            req.setId(id);
            ZuanshiBannerCampaignGetResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            JSONObject one= JSON.parseObject(rsp.getBody());
            Object onee=one.getJSONObject("zuanshi_banner_campaign_get_response");
            JSONObject two=JSON.parseObject(onee.toString());
            Object twoo=two.getJSONObject("result");
            System.out.println(twoo.toString());
            JSONObject three=JSON.parseObject(twoo.toString());
            Object thre=three.getJSONObject("campaign");
            CampaignDetails campaignDetails=new CampaignDetails();
            if (thre==null){
                campaignDetails.setCampaignId(campaignList.getCampaignId());
                campaignDetails.setCampaignName(campaignList.getCampaignName());
                campaignDetails.setCampaignType(campaignList.getCampaignType());
                campaignDetails.setDayBudget(campaignList.getDayBudget());
                campaignDetails.setEndTime(campaignList.getEndTime());
                campaignDetails.setStartTime(campaignList.getStartTime());
                campaignDetails.setOnlineStatus(campaignList.getOnlineStatus());
                campaignDetails.setSpeedType(campaignList.getSpeedType());
                campaignDetails.setAreaIdList("信息不详,无法定位");
                campaignDetails.setTaobaoUserId(campaignList.getTaobaoUserId());
                campaignDetails.setWorkdays(campaignList.getWorkdays());
                campaignDetails.setWeekEnds(campaignList.getWeekEnds());

                campaignDetailsMapper.insertOrUpdate(campaignDetails);
                continue;
            }
            else {
                JSONObject four=JSON.parseObject(thre.toString());
                JSONObject fourr=four.getJSONObject("area_id_list");
                JSONObject num=JSON.parseObject(fourr.toString());
                JSONArray numm=num.getJSONArray("number");;
                String nu=numm.toString().substring(1,numm.toString().length()-1);
                campaignDetails.setAreaIdList(nu);
                campaignDetails.setTaobaoUserId(campaignList.getTaobaoUserId());
                campaignDetails.setWorkdays(campaignList.getWorkdays());
                campaignDetails.setWeekEnds(campaignList.getWeekEnds());
                campaignDetails.setCampaignId(campaignList.getCampaignId());
                campaignDetails.setCampaignName(campaignList.getCampaignName());
                campaignDetails.setCampaignType(campaignList.getCampaignType());
                campaignDetails.setDayBudget(campaignList.getDayBudget());
                campaignDetails.setEndTime(campaignList.getEndTime());
                campaignDetails.setStartTime(campaignList.getStartTime());
                campaignDetails.setOnlineStatus(campaignList.getOnlineStatus());
                campaignDetails.setSpeedType(campaignList.getSpeedType());

                campaignDetailsMapper.insertOrUpdate(campaignDetails);
            }

        }
        return "";
    }
}
