package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.RelationshopConditionMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.RelationshopCondition;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.RelationshopConditionService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerRelationshopPackageConditionFindRequest;
import com.taobao.api.response.ZuanshiBannerRelationshopPackageConditionFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 智钻获取店铺型定向店铺包条件
 */
@Service
public class RelationshopConditionServiceImpl implements RelationshopConditionService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private RelationshopConditionMapper relationshopConditionMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String RelationshopCondition(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerRelationshopPackageConditionFindRequest req = new ZuanshiBannerRelationshopPackageConditionFindRequest();
            ZuanshiBannerRelationshopPackageConditionFindResponse rsp = null;
            try {
                rsp = client.execute(req, tau.getAccessToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println("智钻获取店铺型定向店铺包条件 : " + rsp.getBody());
            //解析响应返回的json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_banner_relationshop_package_condition_find_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject condition = thrObject.getJSONObject("shop_package_query_condition");
            RelationshopCondition rc = new RelationshopCondition();
            JSONObject object = JSON.parseObject(condition.toString());
            //将三个并列的数组嵌套循环遍历
            JSONObject preferenceList = object.getJSONObject("shop_preference_list");
            JSONArray preference = preferenceList.getJSONArray("shop_preference_d_t_o");
            System.out.println(preference);
            for (Object o :preference.toArray()) {
                JSONObject one = JSON.parseObject(o.toString());
                System.out.println(one);
                JSONObject catList = object.getJSONObject("cat_list");
                if (catList == null){
                    rc = new RelationshopCondition();
                    rc.setTaobaoUserId(tau.getTaobaoUserId());
                    rc.setShopPreferenceName(one.getString("shop_preference_name"));
                    rc.setShopPreferenceValue(one.getString("shop_preference_value"));
                    rc.setCateId("0");
                    rc.setCateName("0");
                    rc.setShopScaleId("0");
                    rc.setShopScaleName("0");
                    rc.setMaxPerSale(object.getLong("max_per_sale")==null?0L:object.getLong("max_per_sale"));
                    rc.setMinPerSale(object.getLong("min_per_sale")==null?0L:object.getLong("min_per_sale"));
                    relationshopConditionMapper.insert(rc);
                }else {
                    JSONArray category = catList.getJSONArray("category_d_t_o");
                    for (Object ob :category.toArray()) {
                        JSONObject two = JSON.parseObject(ob.toString());
                        JSONObject shopScale = object.getJSONObject("shop_scale_list");
                        if (shopScale == null){
                            rc = new RelationshopCondition();
                            rc.setTaobaoUserId(tau.getTaobaoUserId());
                            rc.setMaxPerSale(object.getLong("max_per_sale")==null?0L:object.getLong("max_per_sale"));
                            rc.setMinPerSale(object.getLong("min_per_sale")==null?0L:object.getLong("min_per_sale"));
                            rc.setShopPreferenceName(one.getString("shop_preference_name"));
                            rc.setShopPreferenceValue(one.getString("shop_preference_value"));
                            rc.setCateId(two.getString("cate_id"));
                            rc.setCateName(two.getString("cate_name"));
                            rc.setShopScaleId("0");
                            rc.setShopScaleName("0");
                        }else {
                            JSONArray scale = shopScale.getJSONArray("shop_scale_d_t_o");
                            for (Object obj:scale.toArray()) {
                                JSONObject three = JSON.parseObject(obj.toString());
                                rc = new RelationshopCondition();
                                rc.setTaobaoUserId(tau.getTaobaoUserId());
                                rc.setMaxPerSale(object.getLong("max_per_sale")==null?0L:object.getLong("max_per_sale"));
                                rc.setMinPerSale(object.getLong("min_per_sale")==null?0L:object.getLong("min_per_sale"));
                                rc.setShopPreferenceName(one.getString("shop_preference_name"));
                                rc.setShopPreferenceValue(one.getString("shop_preference_value"));
                                rc.setCateId(two.getString("cate_id"));
                                rc.setCateName(two.getString("cate_name"));
                                rc.setShopScaleId(three.getString("shop_scale_id"));
                                rc.setShopScaleName(three.getString("shop_scale_name"));
                                relationshopConditionMapper.insert(rc);

                            }
                        }
                    }
                }
            }
        }

        return "";
    }
}
