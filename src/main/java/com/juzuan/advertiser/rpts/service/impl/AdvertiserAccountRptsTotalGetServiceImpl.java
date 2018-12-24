package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserAccountRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsTotalGet;
import com.juzuan.advertiser.rpts.model.AdzoneRptsDay;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdvertiserAccountRptsTotalGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAccountRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAccountRptsTotalGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiserAccountRptsTotalGetServiceImpl implements AdvertiserAccountRptsTotalGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdvertiserAccountRptsTotalGetMapper advertiserAccountRptsTotalGetMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public void parseAndSaveAccountTotal(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser taobaoAuthorizeUser:taobaoAuthorizeUsers){
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiAdvertiserAccountRptsTotalGetRequest req = new ZuanshiAdvertiserAccountRptsTotalGetRequest();
            req.setStartTime("2018-08-29");
            req.setEndTime("2018-11-27");
            req.setEffect(7L);
            //req.setCampaignModel(1L);
            req.setEffectType("click");
            ZuanshiAdvertiserAccountRptsTotalGetResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());

            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_advertiser_account_rpts_total_get_response");
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject twoo=two.getJSONObject("account_offline_rpt_total_list");//包含data的json对象
            if (twoo.size()==0){
                System.out.println("空");
            }else{  JSONObject thre=JSON.parseObject(twoo.toString());
                JSONArray three=thre.getJSONArray("data");

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
                    //创表中对象
                    AdvertiserAccountRptsTotalGet advertiserAccountRptsTotalGet=new AdvertiserAccountRptsTotalGet();

                    BeanUtils.copyProperties(adzoneRptsDay,advertiserAccountRptsTotalGet);


                    if (Double.parseDouble(advertiserAccountRptsTotalGet.getClick())==0){
                        advertiserAccountRptsTotalGet.setCommodityPurchaseRate("0");//点击量为零
                        advertiserAccountRptsTotalGet.setCommodityCollectionRate("0");
                        advertiserAccountRptsTotalGet.setTotalCollectionRate("0");
                    }
                    else {
                        advertiserAccountRptsTotalGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getCartNum())/Double.parseDouble(advertiserAccountRptsTotalGet.getClick())));//加购率=添加购物车量/点击量
                        advertiserAccountRptsTotalGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getInshopItemColNum())/Double.parseDouble(advertiserAccountRptsTotalGet.getClick())));//收藏率=收藏宝贝量/点击量
                        advertiserAccountRptsTotalGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getClick())/Double.parseDouble(advertiserAccountRptsTotalGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                    }
                    Double collectionAndBuy=Double.parseDouble(advertiserAccountRptsTotalGet.getDirShopColNum())+Double.parseDouble(advertiserAccountRptsTotalGet.getInshopItemColNum())+Double.parseDouble(advertiserAccountRptsTotalGet.getCartNum());
                    if (collectionAndBuy==0){
                        advertiserAccountRptsTotalGet.setTotalCollectionPlusCost("0");
                    }
                    else {
                        advertiserAccountRptsTotalGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                    }
                    if (Double.parseDouble(advertiserAccountRptsTotalGet.getInshopItemColNum())==0){
                        advertiserAccountRptsTotalGet.setCommodityCollectionCost("0");
                    } else {
                        advertiserAccountRptsTotalGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getCharge())/Double.parseDouble(advertiserAccountRptsTotalGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                    }
                    if (Double.parseDouble(advertiserAccountRptsTotalGet.getCartNum())==0){
                        advertiserAccountRptsTotalGet.setCommodityPlusCost("0");
                    }
                    else {
                        advertiserAccountRptsTotalGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getCharge())/Double.parseDouble(advertiserAccountRptsTotalGet.getCartNum())));//加购成本=消耗/添加购物车量
                    }
                    if (Double.parseDouble(advertiserAccountRptsTotalGet.getUv())==0){
                        advertiserAccountRptsTotalGet.setAverageUvValue("0");
                    }
                    else {
                        advertiserAccountRptsTotalGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAccountRptsTotalGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                    }
                    if (Double.parseDouble(advertiserAccountRptsTotalGet.getAlipayInshopAmt())==0){
                        advertiserAccountRptsTotalGet.setOrderAverageAmount("0");
                    }
                    else {
                        advertiserAccountRptsTotalGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAccountRptsTotalGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                    }
                    if (Double.parseDouble(advertiserAccountRptsTotalGet.getCharge())==0){
                        advertiserAccountRptsTotalGet.setAverageCostOfOrder("0");
                    }
                    else {
                        advertiserAccountRptsTotalGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserAccountRptsTotalGet.getCharge())/Double.parseDouble(advertiserAccountRptsTotalGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                    }
                    advertiserAccountRptsTotalGet.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                     /*  Map<String,Object> data=new HashMap<>();
                       data.put("currIndex",0);
                       data.put("pageSize",10);
                      List<AdvertiserAdzoneRptsDayGet> advertiserAdzoneRptsDayGets=advertiserAdzoneRptsDayGetMapper.queryAdzoneRptsDayGetsBySql(data);*/
                    advertiserAccountRptsTotalGetMapper.insert(advertiserAccountRptsTotalGet);

                }
            }
        }
    }
}
