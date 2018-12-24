package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.IndustryshopListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.IndustryshopList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.IndustryshopListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerIndustryshopFindRequest;
import com.taobao.api.response.ZuanshiBannerIndustryshopFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryshopListServiceImpl implements IndustryshopListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private IndustryshopListMapper industryshopListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String IndustryshopList(){
        List<CampaignList> campaignLists = campaignListMapper.selectDistinct();
        for (CampaignList cl : campaignLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerIndustryshopFindRequest req = new ZuanshiBannerIndustryshopFindRequest();
            req.setCampaignType(Long.valueOf(cl.getCampaignType()));
            ZuanshiBannerIndustryshopFindResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("智钻获取行业店铺定向标签选项列表 : "+rsp.getBody());
            //解析json串
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject industry = oneObject.getJSONObject("zuanshi_banner_industryshop_find_response");
            JSONObject twoObject = JSON.parseObject(industry.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject shops = thrObject.getJSONObject("industry_shops");
            JSONObject fouObject = JSON.parseObject(shops.toString());
            JSONArray shop = fouObject.getJSONArray("industry_shop_d_t_o");
            //判断数组是否为空
            if (shop == null){
                continue;
            } else {
                List<IndustryshopList> industryshopLists = JSONObject.parseArray(shop.toString(),IndustryshopList.class);
                for (IndustryshopList il: industryshopLists) {
                    il.setTaobaoUserId(cl.getTaobaoUserId());
                    industryshopListMapper.insert(il);
                }
            }
        }
        return "";
    }
}
