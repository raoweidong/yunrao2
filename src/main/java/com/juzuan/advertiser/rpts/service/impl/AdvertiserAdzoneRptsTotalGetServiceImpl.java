package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserAdzoneRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsTotalGet;
import com.juzuan.advertiser.rpts.model.AdzoneRptsDay;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAdzoneRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAdzoneRptsTotalGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiserAdzoneRptsTotalGetServiceImpl {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdvertiserAdzoneRptsTotalGetMapper advertiserAdzoneRptsTotalGetMapper;
    //@Scheduled(cron ="*/5 * * * * ?")
    public void parseAndSaveAdzoneTotal(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser user:taobaoAuthorizeUsers){
            String sessionKey=user.getAccessToken();
            TaobaoClient client=new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiAdvertiserAdzoneRptsTotalGetRequest req=new ZuanshiAdvertiserAdzoneRptsTotalGetRequest();
            req.setStartTime("2018-08-29");
            req.setEndTime("2018-11-27");
            req.setEffect(7L);
            req.setCampaignModel(1L);
            req.setEffectType("click");
            req.setPageSize(200L);
            req.setOffset(0L);
            ZuanshiAdvertiserAdzoneRptsTotalGetResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());
            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_advertiser_adzone_rpts_total_get_response");
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject twoo=two.getJSONObject("adzone_offline_rpt_total_list");//包含data的json对象
            if (twoo.size()==0){
                System.out.println("空");
            }else{  JSONObject three=JSON.parseObject(twoo.toString());
                JSONArray threes=three.getJSONArray("data");
              /*  for (Object ob:threes.toArray()){
                    System.out.println("数组  "+ob.toString());
                }*/

                List<AdzoneRptsDay> adzoneRptsDays=JSONObject.parseArray(threes.toString(),AdzoneRptsDay.class);
                for (AdzoneRptsDay adzoneRptsDay:adzoneRptsDays){
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

                    AdvertiserAdzoneRptsTotalGet advertiserAdzoneRptsTotalGet=new AdvertiserAdzoneRptsTotalGet();

                    BeanUtils.copyProperties(adzoneRptsDay,advertiserAdzoneRptsTotalGet);


                    if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())==0){
                        advertiserAdzoneRptsTotalGet.setCommodityPurchaseRate("0");//点击量为零
                        advertiserAdzoneRptsTotalGet.setCommodityCollectionRate("0");
                        advertiserAdzoneRptsTotalGet.setTotalCollectionRate("0");
                    }
                    else {
                        advertiserAdzoneRptsTotalGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())));//加购率=添加购物车量/点击量
                        advertiserAdzoneRptsTotalGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())));//收藏率=收藏宝贝量/点击量
                        advertiserAdzoneRptsTotalGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                    }
                    Double collectionAndBuy=Double.parseDouble(advertiserAdzoneRptsTotalGet.getDirShopColNum())+Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum())+Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum());
                    if (collectionAndBuy==0){
                        advertiserAdzoneRptsTotalGet.setTotalCollectionPlusCost("0");
                    }
                    else {
                        advertiserAdzoneRptsTotalGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                    }
                    if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum())==0){
                        advertiserAdzoneRptsTotalGet.setCommodityCollectionCost("0");
                    } else {
                        advertiserAdzoneRptsTotalGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                    }
                    if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum())==0){
                        advertiserAdzoneRptsTotalGet.setCommodityPlusCost("0");
                    }
                    else {
                        advertiserAdzoneRptsTotalGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum())));//加购成本=消耗/添加购物车量
                    }
                    if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getUv())==0){
                        advertiserAdzoneRptsTotalGet.setAverageUvValue("0");
                    }
                    else {
                        advertiserAdzoneRptsTotalGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                    }
                    if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt())==0){
                        advertiserAdzoneRptsTotalGet.setOrderAverageAmount("0");
                    }
                    else {
                        advertiserAdzoneRptsTotalGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                    }
                    if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge())==0){
                        advertiserAdzoneRptsTotalGet.setAverageCostOfOrder("0");
                    }
                    else {
                        advertiserAdzoneRptsTotalGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge())/Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                    }
                    advertiserAdzoneRptsTotalGet.setTaobaoUserId(user.getTaobaoUserId());
                   /* Map<String,Object> data=new HashMap<>();
                    data.put("currIndex",0);
                    data.put("pageSize",10);
                    List<AdvertiserAdzoneRptsDayGet> advertiserAdzoneRptsDayGets=advertiserAdzoneRptsDayGetMapper.queryAdzoneRptsDayGetsBySql(data);*/
                    advertiserAdzoneRptsTotalGetMapper.insert(advertiserAdzoneRptsTotalGet);

                }
                System.out.println("hah");

            }

        }
        }





    }

