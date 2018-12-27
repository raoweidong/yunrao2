package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.DmpTargetListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.DmpTargetList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.DmpTargetListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerDmpFindRequest;
import com.taobao.api.response.ZuanshiBannerDmpFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取DMP定向可用人群列表
 */
@Service
public class DmpTargetListServiceImpl implements DmpTargetListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private DmpTargetListMapper dmpTargetListMapper;
    //定时更新，每天2:00
    @Scheduled(cron = "0 0 2 * * ?")
    public String DmpTargetList(){
        dmpTargetListMapper.deleteALL();
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerDmpFindRequest req = new ZuanshiBannerDmpFindRequest();
            ZuanshiBannerDmpFindResponse rsp = null;
            try {
                rsp = client.execute(req,tau.getAccessToken());
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println(" 获取DMP定向可用人群列表 : "+rsp.getBody());
            //解析响应返回的json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_banner_dmp_find_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject crowds = thrObject.getJSONObject("crowds");
            JSONObject fouObject = JSON.parseObject(crowds.toString());
            JSONArray dto = fouObject.getJSONArray("dmp_crowd_d_t_o");
            //判断数组是否为空
            if (dto == null){
                continue;
            } else {
                List<DmpTargetList> dmpTargetLists = JSONObject.parseArray(dto.toString(),DmpTargetList.class);
                for (DmpTargetList dtl: dmpTargetLists) {
                    dtl.setTaobaoUserId(tau.getTaobaoUserId());
                    dmpTargetListMapper.insert(dtl);
                }
            }
        }
        return "";
    }

}
