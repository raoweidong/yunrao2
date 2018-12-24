package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.*;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.RelationshopLabel;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.RelationshopLabelService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerRelationshopMineFindRequest;
import com.taobao.api.response.ZuanshiBannerRelationshopMineFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshopLabelServiceImpl implements RelationshopLabelService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private RelationshopLabelMapper relationshopLabelMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String RelationshopList(){
        //调用淘宝API,发起请求
        List<CampaignList> campaignLists = campaignListMapper.selectDistinct();
        for (CampaignList cl: campaignLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerRelationshopMineFindRequest req = new ZuanshiBannerRelationshopMineFindRequest();
            req.setCampaignType(Long.valueOf(cl.getCampaignType()));
            ZuanshiBannerRelationshopMineFindResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            }catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("获取店铺型定向-本店行为细分标签列表 : "+rsp.getBody());
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_banner_relationshop_mine_find_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject threeObject = JSON.parseObject(result.toString());
            JSONObject mines = threeObject.getJSONObject("shop_mines");
            JSONObject fourObject = JSON.parseObject(mines.toString());
            JSONArray mine = fourObject.getJSONArray("relation_shop_of_mine_d_t_o");
            if (mine ==null){
                continue;
            } else {
                for ( Object ob:mine.toArray()) {
                    RelationshopLabel rl = null;
                    JSONObject dto = JSON.parseObject(ob.toString());
                    JSONObject windows = dto.getJSONObject("time_windows");
                    if (windows==null){
                        rl  = new RelationshopLabel();
                        rl.setTaobaoUserId(cl.getTaobaoUserId());
                        rl.setBehaviorName(dto.getString("behavior_name"));
                        rl.setBehaviorValue(dto.getString("behavior_value"));
                        rl.setTimeWindowName("0");
                        rl.setTimeWindowValue("0");
                        relationshopLabelMapper.insert(rl);
                    } else {
                        JSONArray window = windows.getJSONArray("time_window_d_t_o");
                        for ( Object object : window.toArray()) {
                            rl  = new RelationshopLabel();
                            JSONObject time = JSON.parseObject(object.toString());
                            rl.setTaobaoUserId(cl.getTaobaoUserId());
                            rl.setBehaviorName(dto.getString("behavior_name"));
                            rl.setBehaviorValue(dto.getString("behavior_value"));
                            rl.setTimeWindowName(time.getString("time_window_name"));
                            rl.setTimeWindowValue(time.getString("time_window_value"));
                            relationshopLabelMapper.insert(rl);
                        }
                    }
                }
            }
        }
            return "";
    }
}
