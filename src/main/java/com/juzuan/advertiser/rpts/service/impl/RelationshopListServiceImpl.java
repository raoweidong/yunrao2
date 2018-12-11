package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.RelationshopListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.RelationshopList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.RelationshopListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerRelationshopPackageFindRequest;
import com.taobao.api.response.ZuanshiBannerRelationshopPackageFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshopListServiceImpl implements RelationshopListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private RelationshopListMapper relationshopListMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    public String Relationshop(){
        List<CampaignList> campaignLists = campaignListMapper.selectDistinct();
        for (CampaignList cl: campaignLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser =taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerRelationshopPackageFindRequest req = new ZuanshiBannerRelationshopPackageFindRequest();
            req.setCampaignType(Long.valueOf(cl.getCampaignType()));
            ZuanshiBannerRelationshopPackageFindResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            }catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("智钻获取店铺型定向店铺包列表 : "+rsp.getBody());
            //解析json串
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject relationshop = oneObject.getJSONObject("zuanshi_banner_relationshop_package_find_response");
            JSONObject twoObject = JSON.parseObject(relationshop.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject shop = thrObject.getJSONObject("shop_packages");
            JSONObject fourObject = JSON.parseObject(shop.toString());
            JSONArray relation = fourObject.getJSONArray("relation_shop_package_dto");
            //判断数组是否为空
            if (relation ==null){
                continue;
            } else {
                List<RelationshopList> relationshopLists = JSONObject.parseArray(relation.toString(),RelationshopList.class);
                for (RelationshopList rl: relationshopLists) {
                    relationshopListMapper.insert(rl);
                }
            }
        }
        return "";
    }

}
