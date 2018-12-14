package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CpcTargetListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CpcTargetList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CpcTargetListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCpcTargetingFindRequest;
import com.taobao.api.response.ZuanshiBannerCpcTargetingFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpcTargetListServiceImpl implements CpcTargetListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CpcTargetListMapper cpcTargetListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String CpcTargeList(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers =taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerCpcTargetingFindRequest req = new ZuanshiBannerCpcTargetingFindRequest();
            ZuanshiBannerCpcTargetingFindResponse rsp = null;
            try {
                rsp = client.execute(req,tau.getAccessToken());
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("获取全店cpc计划可用定向列表 : "+rsp.getBody());
            //解析响应的json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject cpc = oneObject.getJSONObject("zuanshi_banner_cpc_targeting_find_response");
            JSONObject twoObject = JSON.parseObject(cpc.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject targetings = thrObject.getJSONObject("targetings");
            JSONObject fouObject = JSON.parseObject(targetings.toString());
            JSONArray dto = fouObject.getJSONArray("target_d_t_o");
            //判断数组是否为空
            if (dto == null){
                continue;
            } else {
                //反射
                List<CpcTargetList> cpcTargetLists = JSONObject.parseArray(dto.toString(),CpcTargetList.class);
                for (CpcTargetList ctl: cpcTargetLists) {
                    ctl.setTaobaoUserId(tau.getTaobaoUserId());
                    cpcTargetListMapper.insert(ctl);
                }
            }

        }

        return "";
    }

}
