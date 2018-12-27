package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TargetCatListMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TargetCatList;
import com.juzuan.advertiser.rpts.service.TargetCatListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCatFindRequest;
import com.taobao.api.response.ZuanshiBannerCatFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 高级兴趣点-类目查询
 */
@Service
public class TargetCatListServiceImpl implements TargetCatListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private TargetCatListMapper targetCatListMapper;
    //定时更新，每天2:05
    //@Scheduled(cron = "0 5 2 * * ?")
    public String TargetCatList(){
        targetCatListMapper.deleteALL();
        List<CampaignList> campaignLists = campaignListMapper.selectDistinct();
        for (CampaignList cl: campaignLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerCatFindRequest req = new ZuanshiBannerCatFindRequest();
            req.setCampaignType(Long.valueOf(cl.getCampaignType()));
            ZuanshiBannerCatFindResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("高级兴趣点-类目查询 : "+rsp.getBody());
            //解析json
            JSONObject one = JSON.parseObject(rsp.getBody());
            JSONObject bcf = one.getJSONObject("zuanshi_banner_cat_find_response");
            JSONObject two = JSON.parseObject(bcf.toString());
            JSONObject result = two.getJSONObject("result");
            JSONObject thr = JSON.parseObject(result.toString());
            JSONObject interests = thr.getJSONObject("interests");
            JSONObject fou = JSON.parseObject(interests.toString());
            //获取数组
            JSONArray dto = fou.getJSONArray("senior_interest_dto");
            //判断数组是否为空
            if (dto == null){
                continue;
            } else {
                List<TargetCatList> targetCatLists = JSONObject.parseArray(dto.toString(),TargetCatList.class);
                //遍历数组对象
                for (TargetCatList tcl: targetCatLists) {
                    tcl.setCampaignType(Long.valueOf(cl.getCampaignType()));
                    //将数组数据插入表
                    tcl.setTaobaoUserId(cl.getTaobaoUserId());
                    targetCatListMapper.insert(tcl);
                }
            }
        }
        return "";
    }

}
