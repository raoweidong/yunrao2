package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.*;
import com.juzuan.advertiser.rpts.model.*;
import com.juzuan.advertiser.rpts.service.AdvertiserAdzoneRptsDayGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAdzoneRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAdzoneRptsDayGetResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdvertiserAdzoneRptsDayGetServiceImpl implements AdvertiserAdzoneRptsDayGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdzoneListMapper adzoneListMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private AdvertiserAdzoneRptsDayGetMapper advertiserAdzoneRptsDayGetMapper;
    @Autowired
    private AdzoneListBindMapper adzoneListBindMapper;
   //@Scheduled(cron = "*/5 * * * * ?")
    public String getAdzone(){

          List<AdzoneListBind> adzoneListBindd=adzoneListBindMapper.selectAllAdzoneListBind();
           for (AdzoneListBind ad:adzoneListBindd){
               Long adg=ad.getCampaignId();
               Long cam=ad.getAdgroupId();
               Long adz= ad.getAdzoneId();
               String userId=ad.getTaobaoUserId();
               TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.slectByUserId(userId);
               String sessionKey=taobaoAuthorizeUser.getAccessToken();
               TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
               ZuanshiAdvertiserAdzoneRptsDayGetRequest req = new ZuanshiAdvertiserAdzoneRptsDayGetRequest();
               req.setStartTime("2018-08-29");
               req.setEndTime("2018-11-27");
               req.setCampaignId(adg);
               req.setAdgroupId(cam);
               req.setAdzoneId(adz);
               req.setEffect(7L);
               req.setCampaignModel(1L);
               req.setEffectType("click");
               ZuanshiAdvertiserAdzoneRptsDayGetResponse rsp = null;
               try {
                   rsp = client.execute(req, sessionKey);
               } catch (ApiException e) {
                   e.printStackTrace();
               }
               System.out.println("钻展资源位分日列表数据 "+rsp.getBody());

               JSONObject one= JSON.parseObject(rsp.getBody());
               JSONObject onee=one.getJSONObject("zuanshi_advertiser_adzone_rpts_day_get_response");
               JSONObject two=JSON.parseObject(onee.toString());
               JSONObject twoo=two.getJSONObject("adzone_offline_rpt_days_list");//包含data的json对象
               if (twoo.size()==0){
                   System.out.println("为空");
               }else{  JSONObject thre=JSON.parseObject(twoo.toString());
                   JSONArray three=thre.getJSONArray("data");
                   for (Object ob:three.toArray()){
                       System.out.println("数组  "+ob.toString());
                   }

                   List<AdzoneRptsDay> adzoneRptsDayss=JSONObject.parseArray(three.toString(),AdzoneRptsDay.class);
                   for (AdzoneRptsDay adzoneRptsDay:adzoneRptsDayss){
                       adzoneRptsDay.setAdgroupName(adzoneRptsDay.getAdzoneName()==null?"0":adzoneRptsDay.getAdzoneName());
                       adzoneRptsDay.setCampaignId(adzoneRptsDay.getCampaignId()==null?"0":adzoneRptsDay.getCampaignId());
                       adzoneRptsDay.setAdgroupId(adzoneRptsDay.getAdgroupId()==null?"0":adzoneRptsDay.getAdgroupId());
                       adzoneRptsDay.setAdzoneId(adzoneRptsDay.getAdzoneId()==null?"0":adzoneRptsDay.getAdzoneId());
                       adzoneRptsDay.setAdgroupName(adzoneRptsDay.getAdzoneName()==null?"0":adzoneRptsDay.getAdzoneName());
                       adzoneRptsDay.setCampaignName(adzoneRptsDay.getCampaignName()==null?"0":adzoneRptsDay.getCampaignName());
                       adzoneRptsDay.setCvr(adzoneRptsDay.getCvr()==null?"0":adzoneRptsDay.getCvr());
                       adzoneRptsDay.setAlipayInShopNum(adzoneRptsDay.getAlipayInShopNum()==null?"0":adzoneRptsDay.getAlipayInShopNum());
                       adzoneRptsDay.setAlipayInshopAmt(adzoneRptsDay.getAlipayInshopAmt()==null?"0":adzoneRptsDay.getAlipayInshopAmt());
                       adzoneRptsDay.setGmvInshopAmt(adzoneRptsDay.getGmvInshopAmt()==null?"0":adzoneRptsDay.getGmvInshopAmt());
                       adzoneRptsDay.setGmvInshopNum(adzoneRptsDay.getGmvInshopNum()==null?"0":adzoneRptsDay.getGmvInshopNum());
                       adzoneRptsDay.setCartNum(adzoneRptsDay.getCartNum()==null?"0":adzoneRptsDay.getCartNum());
                       adzoneRptsDay.setDirShopColNum(adzoneRptsDay.getDirShopColNum()==null?"0":adzoneRptsDay.getDirShopColNum());
                       adzoneRptsDay.setInshopItemColNum(adzoneRptsDay.getInshopItemColNum()==null?"0":adzoneRptsDay.getInshopItemColNum());
                       adzoneRptsDay.setAvgAccessPageNum(adzoneRptsDay.getAvgAccessPageNum()==null?"0":adzoneRptsDay.getAvgAccessPageNum());
                       adzoneRptsDay.setAvgAccessTime(adzoneRptsDay.getAvgAccessTime()==null?"0":adzoneRptsDay.getAvgAccessTime());
                       adzoneRptsDay.setDeepInshopUv(adzoneRptsDay.getDeepInshopUv()==null?"0":adzoneRptsDay.getDeepInshopUv());
                       adzoneRptsDay.setUv(adzoneRptsDay.getUv()==null?"0":adzoneRptsDay.getUv());
                       adzoneRptsDay.setEcpm(adzoneRptsDay.getEcpm()==null?"0":adzoneRptsDay.getEcpm());
                       adzoneRptsDay.setEcpc(adzoneRptsDay.getEcpc()==null?"0":adzoneRptsDay.getEcpc());
                       adzoneRptsDay.setCtr(adzoneRptsDay.getCtr()==null?"0":adzoneRptsDay.getCtr());
                       adzoneRptsDay.setCharge(adzoneRptsDay.getCharge()==null?"0":adzoneRptsDay.getCharge());
                       adzoneRptsDay.setClick(adzoneRptsDay.getClick()==null?"0":adzoneRptsDay.getClick());
                       adzoneRptsDay.setAdPv(adzoneRptsDay.getAdPv()==null?"0":adzoneRptsDay.getAdPv());
                       adzoneRptsDay.setRoi(adzoneRptsDay.getRoi()==null?"0":adzoneRptsDay.getRoi());
                       adzoneRptsDay.setLogDate(adzoneRptsDay.getLogDate()==null?"2018-00-00":adzoneRptsDay.getLogDate());

                       AdvertiserAdzoneRptsDayGet advertiserAdzoneRptsDayGet=new AdvertiserAdzoneRptsDayGet();

                       BeanUtils.copyProperties(adzoneRptsDay,advertiserAdzoneRptsDayGet);


                       if (Double.parseDouble(advertiserAdzoneRptsDayGet.getClick())==0){
                           advertiserAdzoneRptsDayGet.setCommodityPurchaseRate("0");//点击量为零
                           advertiserAdzoneRptsDayGet.setCommodityCollectionRate("0");
                           advertiserAdzoneRptsDayGet.setTotalCollectionRate("0");
                       }
                       else {
                           advertiserAdzoneRptsDayGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum())/Double.parseDouble(advertiserAdzoneRptsDayGet.getClick())));//加购率=添加购物车量/点击量
                           advertiserAdzoneRptsDayGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum())/Double.parseDouble(advertiserAdzoneRptsDayGet.getClick())));//收藏率=收藏宝贝量/点击量
                           advertiserAdzoneRptsDayGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getClick())/Double.parseDouble(advertiserAdzoneRptsDayGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                       }
                       Double collectionAndBuy=Double.parseDouble(advertiserAdzoneRptsDayGet.getDirShopColNum())+Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum())+Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum());
                       if (collectionAndBuy==0){
                           advertiserAdzoneRptsDayGet.setTotalCollectionPlusCost("0");
                       }
                       else {
                           advertiserAdzoneRptsDayGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                       }
                       if (Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum())==0){
                           advertiserAdzoneRptsDayGet.setCommodityCollectionCost("0");
                       } else {
                           advertiserAdzoneRptsDayGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge())/Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                       }
                       if (Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum())==0){
                           advertiserAdzoneRptsDayGet.setCommodityPlusCost("0");
                       }
                       else {
                           advertiserAdzoneRptsDayGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge())/Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum())));//加购成本=消耗/添加购物车量
                       }
                       if (Double.parseDouble(advertiserAdzoneRptsDayGet.getUv())==0){
                           advertiserAdzoneRptsDayGet.setAverageUvValue("0");
                       }
                       else {
                           advertiserAdzoneRptsDayGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAdzoneRptsDayGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                       }
                       if (Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInshopAmt())==0){
                           advertiserAdzoneRptsDayGet.setOrderAverageAmount("0");
                       }
                       else {
                           advertiserAdzoneRptsDayGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                       }
                       if (Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge())==0){
                           advertiserAdzoneRptsDayGet.setAverageCostOfOrder("0");
                       }
                       else {
                           advertiserAdzoneRptsDayGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge())/Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                       }
                       advertiserAdzoneRptsDayGet.setTaobaoUserId(ad.getTaobaoUserId());
                       Map<String,Object> data=new HashMap<>();
                       data.put("currIndex",0);
                       data.put("pageSize",10);
                      List<AdvertiserAdzoneRptsDayGet> advertiserAdzoneRptsDayGets=advertiserAdzoneRptsDayGetMapper.queryAdzoneRptsDayGetsBySql(data);
                       advertiserAdzoneRptsDayGetMapper.insert(advertiserAdzoneRptsDayGet);

                   }
                   System.out.println("hah");

               }

           }
        return "";
        }

    public static void main(String[] args) {

    }

    }

