package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.SeniorinterestListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.SeniorinterestList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.SeniorinterestListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerSeniorinterestFindRequest;
import com.taobao.api.response.ZuanshiBannerSeniorinterestFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeniorinterestListServiceImpl implements SeniorinterestListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private SeniorinterestListMapper seniorinterestListMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    @Override
    public String SeniorinterestList(){
        List<CampaignList> campaignLists = campaignListMapper.selectDistinct();
        for (CampaignList cl: campaignLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerSeniorinterestFindRequest req = new ZuanshiBannerSeniorinterestFindRequest();
            req.setCampaignType(Long.valueOf(cl.getCampaignType()));
            ZuanshiBannerSeniorinterestFindResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            } catch(ApiException e){
                e.printStackTrace();
            }
            System.out.println("目型定向高级兴趣点集合 : "+rsp.getBody());
            //解析json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject seniorint = oneObject.getJSONObject("zuanshi_banner_seniorinterest_find_response");
            JSONObject twoObiect = JSON.parseObject(seniorint.toString());
            JSONObject result = twoObiect.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject senior = thrObject.getJSONObject("senior_interests");
            JSONObject fouObject = JSON.parseObject(senior.toString());
            JSONArray seniorDto = fouObject.getJSONArray("senior_interest_dto");
            //判断数组是否为空
            if (seniorDto == null){
                continue;
            } else {
                List<SeniorinterestList> seniorinterestLists = JSONObject.parseArray(seniorDto.toString(),SeniorinterestList.class);
                for (SeniorinterestList sl : seniorinterestLists) {
                    seniorinterestListMapper.insert(sl);
                }
            }

        }
        return "";
    }

}
