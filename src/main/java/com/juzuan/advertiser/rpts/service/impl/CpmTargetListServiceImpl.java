package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CpmTargetListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CpmTargetList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CpmTargetListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCpmTargetingFindRequest;
import com.taobao.api.response.ZuanshiBannerCpmTargetingFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpmTargetListServiceImpl implements CpmTargetListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CpmTargetListMapper cpmTargetListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String CpmTargeList(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerCpmTargetingFindRequest req = new ZuanshiBannerCpmTargetingFindRequest();
            ZuanshiBannerCpmTargetingFindResponse rsp = null;
            try {
                rsp = client.execute(req,tau.getAccessToken());
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("获取全店cpm计划可用定向列表 : "+rsp.getBody());
            //解析响应的json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject cpmTargeting = oneObject.getJSONObject("zuanshi_banner_cpm_targeting_find_response");
            JSONObject twoObject = JSON.parseObject(cpmTargeting.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject targetings = thrObject.getJSONObject("targetings");
            JSONObject fouObject = JSON.parseObject(targetings.toString());
            JSONArray dto = fouObject.getJSONArray("target_d_t_o");
            if (dto == null){
                continue;
            } else {
                List<CpmTargetList> cpmTargetLists = JSONObject.parseArray(dto.toString(),CpmTargetList.class);
                for (CpmTargetList cpm : cpmTargetLists) {
                    cpm.setTaobaoUserId(tau.getTaobaoUserId());
                    cpmTargetListMapper.insert(cpm);
                }
            }
        }
        return "";
    }
}
