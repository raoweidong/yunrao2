package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdgroupListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerAdgroupFindRequest;
import com.taobao.api.response.ZuanshiBannerAdgroupFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdgroupListServiceImpl implements AdgroupListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    public String AdgroupList(){
       List<CampaignList> cammmm=campaignListMapper.selectAllCampaign();
       for (CampaignList caa:cammmm){
           String userId=caa.getTaobaoUserId();
           TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.slectByUserId(userId);
           TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
           ZuanshiBannerAdgroupFindRequest req = new ZuanshiBannerAdgroupFindRequest();
           req.setCampaignId(caa.getCampaignId());
           ZuanshiBannerAdgroupFindResponse rsp = null;
           try {
               rsp = client.execute(req, taobaoAuthorizeUser.getAccessToken());
           } catch (ApiException e) {
               e.printStackTrace();
           }
           System.out.println("单元列表  "+rsp.getBody());

           JSONObject oneObject= JSON.parseObject(rsp.getBody());
           //System.out.println(oneObject.toString());
           Object twoObject=oneObject.getJSONObject("zuanshi_banner_adgroup_find_response");
           //System.out.println(twoObject.toString());
           JSONObject threeObject=JSON.parseObject(twoObject.toString());
           Object fourObject=threeObject.getJSONObject("result");
           //System.out.println(fourObject.toString());
           JSONObject fiveObject = JSON.parseObject(fourObject.toString());
           Object sixObject = fiveObject.getJSONObject("adgroups");
           if(sixObject == null){
               continue;
           }else {
               //System.out.println(sixObject.toString());
               JSONObject sevenObject = JSONObject.parseObject(sixObject.toString());
               JSONArray eightObject = sevenObject.getJSONArray("adgroup");
               if (eightObject == null) {
                   continue;
               } else {
                   List<AdgroupList> adgroupList = JSONObject.parseArray(eightObject.toString(), AdgroupList.class);
                   //遍历对象数组
                   for (AdgroupList ad : adgroupList) {
                       //ad.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                       ad.setTaobaoUserId(caa.getTaobaoUserId());
                       ad.setAdgroupSource(0);
                       adgroupListMapper.insertSelective(ad);
                   }
               }
           }
       }
       return "";
    }

    @Override
    public void parseAndsaveAdgroupList(String json){

    }

}
