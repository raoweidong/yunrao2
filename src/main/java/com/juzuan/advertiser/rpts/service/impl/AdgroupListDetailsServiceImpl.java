package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListDetailsMapper;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.AdgroupListDetails;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdgroupListDetailsService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerAdgroupGetRequest;
import com.taobao.api.response.ZuanshiBannerAdgroupGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public  class AdgroupListDetailsServiceImpl implements AdgroupListDetailsService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdgroupListDetailsMapper adgroupListDetailsMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    @Override
    public String AdgroupListDetails (String taobaoUserId) {

        List<AdgroupList> cam = adgroupListMapper.selectAllAdgroup();
        for (AdgroupList ad: cam) {
            String userId = ad.getTaobaoUserId();
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(taobaoUserId);
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerAdgroupGetRequest req = new ZuanshiBannerAdgroupGetRequest();
            req.setId(ad.getAdgroupId());
            ZuanshiBannerAdgroupGetResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println("单元列表详情  :  "+rsp.getBody());

            AdgroupListDetails adgroupListDetail = new AdgroupListDetails();
            JSONObject oneObject= JSON.parseObject(rsp.getBody());
            System.out.println("1  : "+oneObject.toString());
            Object twoObject=oneObject.getJSONObject("zuanshi_banner_adgroup_get_response");
            System.out.println("2  : "+twoObject.toString());
            JSONObject threeObject=JSON.parseObject(twoObject.toString());
            Object fourObject=threeObject.getJSONObject("result");
            System.out.println("4  : "+fourObject.toString());
            JSONObject fivObject=JSONObject.parseObject(fourObject.toString());
            System.out.println("5  : "+fivObject.toString());
            if (fivObject.getJSONObject("adgroup")==null){
                adgroupListDetail.setTaobaoUserId(ad.getTaobaoUserId());
                adgroupListDetail.setCampaignId(ad.getCampaignId());
                adgroupListDetail.setAdgroupId(ad.getAdgroupId());
                adgroupListDetail.setAdgroupName(ad.getAdgroupName());
                adgroupListDetail.setOnlineStatus(0L);
                adgroupListDetail.setIntelligentBid(0);
                adgroupListDetail.setAdboardFilter(0);
                adgroupListDetailsMapper.insert(adgroupListDetail);
                continue;
            }else {
                Object sixObject = fivObject.getJSONObject("adgroup");
                System.out.println("6  : " + sixObject.toString());
                Long camId = ((JSONObject) sixObject).getLong("campaign_id");
                adgroupListDetail.setCampaignId(camId);
                Integer intBid = ((JSONObject) sixObject).getInteger("intelligent_bid");
                adgroupListDetail.setIntelligentBid(intBid);
                Long onl = ((JSONObject) sixObject).getLong("online_status");
                adgroupListDetail.setOnlineStatus(onl);
                Long adgId = ((JSONObject) sixObject).getLong("id");
                adgroupListDetail.setAdgroupId(adgId);
                adgroupListDetail.setAdgroupName(((JSONObject) sixObject).getString("name"));
                Integer adb = ((JSONObject) sixObject).getInteger("adboard_filter");
                adgroupListDetail.setAdboardFilter(adb);
                adgroupListDetail.setTaobaoUserId(ad.getTaobaoUserId());
                adgroupListDetailsMapper.insert(adgroupListDetail);
            }

        }
        return "";
    }

    @Override
    public void parseAndsaveAdgroupListDetails(String json) {
        JSONObject oneObject= JSON.parseObject(json);
        System.out.println("1  : "+oneObject.toString());
        Object twoObject=oneObject.getJSONObject("zuanshi_banner_adgroup_get_response");
        System.out.println("2  : "+twoObject.toString());
        JSONObject threeObject=JSON.parseObject(twoObject.toString());
        Object fourObject=threeObject.getJSONObject("result");
        System.out.println("4  : "+fourObject.toString());
        JSONObject fivObject=JSONObject.parseObject(fourObject.toString());
        System.out.println("5  : "+fivObject.toString());
        Object sixObject = fivObject.getJSONObject("adgroup");
        System.out.println("6  : "+sixObject.toString());

        AdgroupListDetails adgroupListDetail = new AdgroupListDetails();
        Long camId = ((JSONObject) sixObject).getLong("campaign_id");
        adgroupListDetail.setCampaignId(camId);
        Integer intBid = ((JSONObject) sixObject).getInteger("intelligent_bid");
        adgroupListDetail.setIntelligentBid(intBid);
        Long onl = ((JSONObject) sixObject).getLong("online_status");
        adgroupListDetail.setOnlineStatus(onl);
        Long adgId = ((JSONObject) sixObject).getLong("id");
        adgroupListDetail.setAdgroupId(adgId);
        adgroupListDetail.setAdgroupName(((JSONObject) sixObject).getString("name"));
        Integer adb = ((JSONObject) sixObject).getInteger("adboard_filter");
        adgroupListDetail.setAdboardFilter(adb);

        adgroupListDetailsMapper.insert(adgroupListDetail);

    }
}
