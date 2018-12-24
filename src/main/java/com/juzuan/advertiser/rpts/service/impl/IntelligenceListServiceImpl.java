package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.IntelligenceListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.IntelligenceList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.IntelligenceListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerIntelligenceShopScaleFindRequest;
import com.taobao.api.response.ZuanshiBannerIntelligenceShopScaleFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntelligenceListServiceImpl implements IntelligenceListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private IntelligenceListMapper intelligenceListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String IntelligenceList(){
        //调用淘宝API,发起请求
        List<CampaignList> campaignLists = campaignListMapper.selectDistinct();
        for (CampaignList cl: campaignLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerIntelligenceShopScaleFindRequest req = new ZuanshiBannerIntelligenceShopScaleFindRequest();
            req.setCampaignType(Long.valueOf(cl.getCampaignType()));
            ZuanshiBannerIntelligenceShopScaleFindResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("智钻智能定向（店铺优质人群），获取可用人群规模列表 : "+rsp.getBody());
            //解析响应返回的json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_banner_intelligence_shop_scale_find_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject shopCales = thrObject.getJSONObject("intelligence_shop_scales");
            JSONObject fouObject = JSON.parseObject(shopCales.toString());
            JSONArray dto = fouObject.getJSONArray("intelligent_shop_dto");
            //判断数组是否为空
            if (dto == null){
                continue;
            } else {
                List<IntelligenceList> intelligenceLists = JSONObject.parseArray(dto.toString(),IntelligenceList.class);
                for (IntelligenceList il: intelligenceLists) {
                    il.setCampaignType(Long.valueOf(cl.getCampaignType()));
                    intelligenceListMapper.insert(il);
                }
            }
        }
        return "";
    }
}
