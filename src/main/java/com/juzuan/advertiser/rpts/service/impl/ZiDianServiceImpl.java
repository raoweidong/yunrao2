package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoZsRelationshopPackgeTargetConditionMapper;
import com.juzuan.advertiser.rpts.mapper.ZiDianMapper;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TaobaoZsRelationshopPackgeTargetCondition;
import com.juzuan.advertiser.rpts.model.ZiDian;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerRelationshopPackageConditionFindRequest;
import com.taobao.api.response.ZuanshiBannerRelationshopPackageConditionFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZiDianServiceImpl {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private ZiDianMapper ziDianMapper;
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private TaobaoZsRelationshopPackgeTargetConditionMapper taobaoZsRelationshopPackgeTargetConditionMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String getZiDian(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
       for (TaobaoAuthorizeUser au:taobaoAuthorizeUsers){
           String sessionKey=au.getAccessToken();
           String userId=au.getTaobaoUserId();

           TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
           ZuanshiBannerRelationshopPackageConditionFindRequest req = new ZuanshiBannerRelationshopPackageConditionFindRequest();
           ZuanshiBannerRelationshopPackageConditionFindResponse rsp = null;
           try {
               rsp = client.execute(req, sessionKey);
           } catch (ApiException e) {
               e.printStackTrace();
           }
           System.out.println(rsp.getBody());
           JSONObject one= JSON.parseObject(rsp.getBody());
           JSONObject onee=one.getJSONObject("zuanshi_banner_relationshop_package_condition_find_response");
           JSONObject two=JSON.parseObject(onee.toString());
           JSONObject twoo=two.getJSONObject("result");
           JSONObject thre=JSON.parseObject(twoo.toString());
           JSONObject three=thre.getJSONObject("shop_package_query_condition");
           System.out.println("three  "+three.toString());
           JSONObject fou=JSON.parseObject(three.toString());
           System.out.println("fou  "+fou.toString());
           //店铺型定向店铺包条件表
           TaobaoZsRelationshopPackgeTargetCondition taobaoZsRelationshopPackgeTargetCondition=new TaobaoZsRelationshopPackgeTargetCondition();
           String maxPerSale=fou.getString("max_per_sale");
           String minPerSale=fou.getString("min_per_sale");
           String uerId=au.getTaobaoUserId();
           taobaoZsRelationshopPackgeTargetCondition.setMaxPerSale(maxPerSale==null?0:Long.valueOf(maxPerSale));
           taobaoZsRelationshopPackgeTargetCondition.setMinPerSale(minPerSale==null?0:Long.valueOf(minPerSale));
           taobaoZsRelationshopPackgeTargetCondition.setTaobaoUserId(uerId);
           taobaoZsRelationshopPackgeTargetConditionMapper.insert(taobaoZsRelationshopPackgeTargetCondition);

           JSONObject four=fou.getJSONObject("cat_list");
           ZiDian ziDian=new ZiDian();//创建字典对象
           if (four==null){
               System.out.println("类目不存在");
           }else{
               JSONArray categoryDTO=four.getJSONArray("category_d_t_o");
               //数据字典表
               //类目cate___的一条记录
               for (Object ob:categoryDTO.toArray()){
                   JSONObject oc=JSON.parseObject(ob.toString());
                   for (String key:oc.keySet()){
                       System.out.println("key "+key);
                       if (key=="cate_id"){
                           ziDian.setCodeName(key);
                           ziDian.setCodeValue(oc.getString(key));
                       }else {ziDian.setDescName(key);
                           ziDian.setDescValue(oc.getString(key));
                       }
                   }
                   ziDian.setCategoryName("类目");
                  int iD= taobaoZsRelationshopPackgeTargetConditionMapper.selectByUserId(userId).getId();
                   ziDian.setConditonId(Long.valueOf(iD));
                   ziDianMapper.insert(ziDian);
               }
           }


           JSONObject shopPreferenceList=fou.getJSONObject("shop_preference_list");
           if (shopPreferenceList==null){
               System.out.println("店铺规模没有");
           }else{
               JSONArray shopPreferenceD_t_o=shopPreferenceList.getJSONArray("shop_preference_d_t_o");
               //人群优选shop_preference__的一条记录
               for (Object OO:shopPreferenceD_t_o.toArray()){
                   JSONObject OOO=JSON.parseObject(OO.toString());
                   for (String key:OOO.keySet()){
                       if (key=="shop_preference_value"){
                           ziDian.setCodeName(key);
                           ziDian.setCodeValue(OOO.getString(key));
                       }else {ziDian.setDescName(key);
                           ziDian.setDescValue(OOO.getString(key));
                       }
                   }
                   ziDian.setCategoryName("人群优选");
                   int iD= taobaoZsRelationshopPackgeTargetConditionMapper.selectByUserId(userId).getId();
                   ziDian.setConditonId(Long.valueOf(iD));
                   ziDianMapper.insert(ziDian);
               }
           }


           JSONObject shopScaleList=fou.getJSONObject("shop_scale_list");
           if (shopScaleList==null){
               System.out.println("人群优选没有");
           }else {
               JSONArray shopScale_d_t_o=shopScaleList.getJSONArray("shop_scale_d_t_o");
               //店铺规模shop_scale一条记录
               for (Object oo:shopScale_d_t_o.toArray()){
                   JSONObject oT=JSON.parseObject(oo.toString());
                   for (String scale:oT.keySet()){
                       if (scale=="shop_scale_id"){
                           ziDian.setCodeName(scale);
                           ziDian.setCodeValue(oT.getString(scale));
                       }else {
                           ziDian.setDescName(scale);
                           ziDian.setDescValue(oT.getString(scale));
                       }
                   }
                   ziDian.setCategoryName("店铺规模");
                   int iD= taobaoZsRelationshopPackgeTargetConditionMapper.selectByUserId(userId).getId();
                   ziDian.setConditonId(Long.valueOf(iD));
                  // ziDianMapper.insert(ziDian);
               }
           }


       }
        return "";
    }
}
