package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserAccountRptsDayGetMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsDayGet;
import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsTotalGet;
import com.juzuan.advertiser.rpts.model.AdzoneRptsDay;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAccountRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAccountRptsDayGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 钻展广告主账户数据分日列表查询
 */
@Service
public class AdvertiserAccountRptsDayServiceImpl {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdvertiserAccountRptsDayGetMapper advertiserAccountRptsDayGetMapper;
    //@Scheduled(cron = "0 0 3 * * ? ")
    public void parseAndSaveAccountDays(){
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        String time= sdf.format(new java.util.Date());
        System.out.println(time);
        //获取系统前一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        String yestime = sdf.format(calendar.getTime());
        System.out.println(yestime);
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser taobaoAuthorizeUser:taobaoAuthorizeUsers){
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiAdvertiserAccountRptsDayGetRequest req = new ZuanshiAdvertiserAccountRptsDayGetRequest();
            req.setStartTime(yestime);
            req.setEndTime(time);
            req.setEffect(7L);
            //req.setCampaignModel(1L);
            req.setEffectType("click");
            ZuanshiAdvertiserAccountRptsDayGetResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println("钻展广告主账户数据分日列表查询"+rsp.getBody());

            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_advertiser_account_rpts_day_get_response");
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject twoo=two.getJSONObject("account_offline_rpt_days_list");//包含data的json对象
            if (twoo.size()==0){
                System.out.println("空");
            }else{  JSONObject thre=JSON.parseObject(twoo.toString());
                JSONArray three=thre.getJSONArray("data");
                List<AdvertiserAccountRptsDayGet> adzoneRptsDayss=JSONObject.parseArray(three.toString(),AdvertiserAccountRptsDayGet.class);
                for (AdvertiserAccountRptsDayGet adzoneRptsDay:adzoneRptsDayss){
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

                    //创表中对象
                    AdvertiserAccountRptsDayGet advertiserAccountRptsDayGet=new AdvertiserAccountRptsDayGet();
                    BeanUtils.copyProperties(adzoneRptsDay,advertiserAccountRptsDayGet);
                    if (Double.parseDouble(advertiserAccountRptsDayGet.getClick())==0){
                        advertiserAccountRptsDayGet.setCommodityPurchaseRate("0");//点击量为零
                        advertiserAccountRptsDayGet.setCommodityCollectionRate("0");
                        advertiserAccountRptsDayGet.setTotalCollectionRate("0");
                    }
                    else {
                        advertiserAccountRptsDayGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getCartNum())/Double.parseDouble(advertiserAccountRptsDayGet.getClick())));//加购率=添加购物车量/点击量
                        advertiserAccountRptsDayGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getInshopItemColNum())/Double.parseDouble(advertiserAccountRptsDayGet.getClick())));//收藏率=收藏宝贝量/点击量
                        advertiserAccountRptsDayGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getClick())/Double.parseDouble(advertiserAccountRptsDayGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                    }
                    Double collectionAndBuy=Double.parseDouble(advertiserAccountRptsDayGet.getDirShopColNum())+Double.parseDouble(advertiserAccountRptsDayGet.getInshopItemColNum())+Double.parseDouble(advertiserAccountRptsDayGet.getCartNum());
                    if (collectionAndBuy==0){
                        advertiserAccountRptsDayGet.setTotalCollectionPlusCost("0");
                    }
                    else {
                        advertiserAccountRptsDayGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                    }
                    if (Double.parseDouble(advertiserAccountRptsDayGet.getInshopItemColNum())==0){
                        advertiserAccountRptsDayGet.setCommodityCollectionCost("0");
                    } else {
                        advertiserAccountRptsDayGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getCharge())/Double.parseDouble(advertiserAccountRptsDayGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                    }
                    if (Double.parseDouble(advertiserAccountRptsDayGet.getCartNum())==0){
                        advertiserAccountRptsDayGet.setCommodityPlusCost("0");
                    }
                    else {
                        advertiserAccountRptsDayGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getCharge())/Double.parseDouble(advertiserAccountRptsDayGet.getCartNum())));//加购成本=消耗/添加购物车量
                    }
                    if (Double.parseDouble(advertiserAccountRptsDayGet.getUv())==0){
                        advertiserAccountRptsDayGet.setAverageUvValue("0");
                    }
                    else {
                        advertiserAccountRptsDayGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAccountRptsDayGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                    }
                    if (Double.parseDouble(advertiserAccountRptsDayGet.getAlipayInshopAmt())==0){
                        advertiserAccountRptsDayGet.setOrderAverageAmount("0");
                    }
                    else {
                        advertiserAccountRptsDayGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getAlipayInshopAmt())/Double.parseDouble(advertiserAccountRptsDayGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                    }
                    if (Double.parseDouble(advertiserAccountRptsDayGet.getCharge())==0){
                        advertiserAccountRptsDayGet.setAverageCostOfOrder("0");
                    }
                    else {
                        advertiserAccountRptsDayGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserAccountRptsDayGet.getCharge())/Double.parseDouble(advertiserAccountRptsDayGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                    }
                    advertiserAccountRptsDayGet.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                    advertiserAccountRptsDayGetMapper.insert(advertiserAccountRptsDayGet);

                }
            }

        }


    }
}
