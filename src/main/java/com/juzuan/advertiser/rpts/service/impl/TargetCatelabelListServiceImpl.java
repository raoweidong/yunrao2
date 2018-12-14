package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TargetCatelabelListMapper;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TargetCatelabelList;
import com.juzuan.advertiser.rpts.service.TargetCatelabelListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCatelabelFindRequest;
import com.taobao.api.response.ZuanshiBannerCatelabelFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TargetCatelabelListServiceImpl implements TargetCatelabelListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private TargetCatelabelListMapper targetCatelabelListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String TargetCatelabeList(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau : taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerCatelabelFindRequest req = new ZuanshiBannerCatelabelFindRequest();
            ZuanshiBannerCatelabelFindResponse rsp = null;
            try {
                rsp = client.execute(req,tau.getAccessToken());
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("获取群体定向标签列表json : "+rsp.getBody());
            //解析响应json
            JSONObject one = JSON.parseObject(rsp.getBody());
            JSONObject bcfr = one.getJSONObject("zuanshi_banner_catelabel_find_response");
            JSONObject two = JSON.parseObject(bcfr.toString());
            JSONObject result = two.getJSONObject("result");
            JSONObject thr = JSON.parseObject(result.toString());
            JSONObject labels = thr.getJSONObject("labels");
            JSONObject fou = JSON.parseObject(labels.toString());
            JSONArray dto = fou.getJSONArray("cat_label_d_t_o");
            //判断数组是否为空
            if (dto == null){
                continue;
            } else {
                List<TargetCatelabelList> targetCatelabelLists = JSONObject.parseArray(dto.toString(),TargetCatelabelList.class);
                for (TargetCatelabelList tcl: targetCatelabelLists) {
                    targetCatelabelListMapper.insert(tcl);
                }
            }
        }
        return "";
    }
}
