package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CategoryRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.RelationshopConditionMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CategoryRptsTotalGet;
import com.juzuan.advertiser.rpts.model.RelationshopCondition;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CategoryRptsTotalGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiCategoryRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiCategoryRptsTotalGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 获取类目报表数据多日汇总
 */
@Service
public class CategoryRptsTotalGetServiceImpl implements CategoryRptsTotalGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private RelationshopConditionMapper relationshopConditionMapper;
    @Autowired
    private CategoryRptsTotalGetMapper categoryRptsTotalGetMapper;
    @Autowired
    private RequestMapper requestMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String CategoryRptsTotalGet(){
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        String time= sdf.format(new java.util.Date());
        System.out.println(time);
        //获取系统前七天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-7);
        String yestime = sdf.format(calendar.getTime());
        List<Request> requests = requestMapper.selectAllRequest();
        for (Request request:requests) {
            List<RelationshopCondition> relationshopConditions = relationshopConditionMapper.selectDistinct();
            for (RelationshopCondition rc : relationshopConditions) {
                TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(rc.getTaobaoUserId());
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiCategoryRptsTotalGetRequest req = new ZuanshiCategoryRptsTotalGetRequest();
                req.setStartTime(yestime);
                req.setEndTime(time);
                req.setShopMainCatId(rc.getCateId());
                req.setEffect(request.getEffect());
                req.setEffectType(request.getEffectType());
                req.setCampaignModel(request.getCampaignModel());
                ZuanshiCategoryRptsTotalGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("类目报表数据多日汇总: " + rsp.getBody());
                JSONObject oneObject = JSON.parseObject(rsp.getBody());
                JSONObject zuanshi = oneObject.getJSONObject("zuanshi_category_rpts_total_get_response");
                JSONObject twoObject = JSON.parseObject(zuanshi.toString());
                JSONObject total = twoObject.getJSONObject("category_offline_rpt_total_list");
                JSONObject thrObject = JSON.parseObject(total.toString());
                JSONArray data = thrObject.getJSONArray("data");
                if (data == null) {
                    continue;
                } else {
                    for (Object ob : data.toArray()) {
                        CategoryRptsTotalGet crtg = new CategoryRptsTotalGet();
                        JSONObject one = JSON.parseObject(ob.toString());

                        crtg.setTaobaoUserId(rc.getTaobaoUserId());
                        crtg.setCatId(one.getString("shop_main_cat_id"));
                        crtg.setCatName(one.getString("shop_main_cat_name"));
                        crtg.setCtr(one.getString("ctr"));
                        crtg.setEcpc(one.getString("ecpc"));
                        crtg.setEcpm(one.getString("ecpm"));
                        crtg.setCvr(one.getString("cvr"));
                        crtg.setRoi(one.getString("roi"));
                        categoryRptsTotalGetMapper.insert(crtg);
                    }
                }
            }
        }
        return "";
    }


}
