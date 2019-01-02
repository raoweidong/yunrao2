package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserAdzoneRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsTotalGet;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAdzoneRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAdzoneRptsTotalGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    @Autowired
    private RequestMapper requestMapper;
    //定时更新：每周周日3:00
    @Scheduled(cron ="0 0 3 1/7 * ?")
    public void parseAndSaveAdzoneTotal(){
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        String time= sdf.format(new java.util.Date());
        System.out.println(time);
        //获取系统前一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        String yestime = sdf.format(calendar.getTime());
        List<Request> requests = requestMapper.selectAllRequest();
        for (Request request:requests) {
            List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
            for (TaobaoAuthorizeUser user : taobaoAuthorizeUsers) {
                String sessionKey = user.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserAdzoneRptsTotalGetRequest req = new ZuanshiAdvertiserAdzoneRptsTotalGetRequest();
                req.setStartTime(yestime);
                req.setEndTime(time);
                req.setEffect(request.getEffect());
                req.setCampaignModel(request.getCampaignModel());
                req.setEffectType(request.getEffectType());
                req.setPageSize(200L);
                req.setOffset(0L);
                ZuanshiAdvertiserAdzoneRptsTotalGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println(rsp.getBody());
                JSONObject one = JSON.parseObject(rsp.getBody());
                JSONObject onee = one.getJSONObject("zuanshi_advertiser_adzone_rpts_total_get_response");
                JSONObject two = JSON.parseObject(onee.toString());
                JSONObject twoo = two.getJSONObject("adzone_offline_rpt_total_list");//包含data的json对象
                if (twoo.size() == 0) {
                    System.out.println("空");
                } else {
                    JSONObject three = JSON.parseObject(twoo.toString());
                    JSONArray threes = three.getJSONArray("data");
                    List<AdvertiserAdzoneRptsTotalGet> adzoneRptsDays = JSONObject.parseArray(threes.toString(), AdvertiserAdzoneRptsTotalGet.class);
                    for (AdvertiserAdzoneRptsTotalGet advertiserAdzoneRptsTotalGet : adzoneRptsDays) {
                        advertiserAdzoneRptsTotalGet.setEffect(request.getEffect());
                        advertiserAdzoneRptsTotalGet.setEffectType(request.getEffectType());
                        advertiserAdzoneRptsTotalGet.setCampaignModel(request.getCampaignModel());
                        advertiserAdzoneRptsTotalGet.setAdgroupName(advertiserAdzoneRptsTotalGet.getAdzoneName() == null ? "0" : advertiserAdzoneRptsTotalGet.getAdzoneName());
                        advertiserAdzoneRptsTotalGet.setCampaignId(advertiserAdzoneRptsTotalGet.getCampaignId() == null ? "0" : advertiserAdzoneRptsTotalGet.getCampaignId());
                        advertiserAdzoneRptsTotalGet.setAdgroupId(advertiserAdzoneRptsTotalGet.getAdgroupId() == null ? "0" : advertiserAdzoneRptsTotalGet.getAdgroupId());
                        advertiserAdzoneRptsTotalGet.setAdzoneId(advertiserAdzoneRptsTotalGet.getAdzoneId() == null ? "0" : advertiserAdzoneRptsTotalGet.getAdzoneId());
                        advertiserAdzoneRptsTotalGet.setAdgroupName(advertiserAdzoneRptsTotalGet.getAdzoneName() == null ? "0" : advertiserAdzoneRptsTotalGet.getAdzoneName());
                        advertiserAdzoneRptsTotalGet.setCampaignName(advertiserAdzoneRptsTotalGet.getCampaignName() == null ? "0" : advertiserAdzoneRptsTotalGet.getCampaignName());
                        advertiserAdzoneRptsTotalGet.setCvr(advertiserAdzoneRptsTotalGet.getCvr() == null ? "0" : advertiserAdzoneRptsTotalGet.getCvr());
                        advertiserAdzoneRptsTotalGet.setAlipayInShopNum(advertiserAdzoneRptsTotalGet.getAlipayInShopNum() == null ? "0" : advertiserAdzoneRptsTotalGet.getAlipayInShopNum());
                        advertiserAdzoneRptsTotalGet.setAlipayInshopAmt(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt() == null ? "0" : advertiserAdzoneRptsTotalGet.getAlipayInshopAmt());
                        advertiserAdzoneRptsTotalGet.setGmvInshopAmt(advertiserAdzoneRptsTotalGet.getGmvInshopAmt() == null ? "0" : advertiserAdzoneRptsTotalGet.getGmvInshopAmt());
                        advertiserAdzoneRptsTotalGet.setGmvInshopNum(advertiserAdzoneRptsTotalGet.getGmvInshopNum() == null ? "0" : advertiserAdzoneRptsTotalGet.getGmvInshopNum());
                        advertiserAdzoneRptsTotalGet.setCartNum(advertiserAdzoneRptsTotalGet.getCartNum() == null ? "0" : advertiserAdzoneRptsTotalGet.getCartNum());
                        advertiserAdzoneRptsTotalGet.setDirShopColNum(advertiserAdzoneRptsTotalGet.getDirShopColNum() == null ? "0" : advertiserAdzoneRptsTotalGet.getDirShopColNum());
                        advertiserAdzoneRptsTotalGet.setInshopItemColNum(advertiserAdzoneRptsTotalGet.getInshopItemColNum() == null ? "0" : advertiserAdzoneRptsTotalGet.getInshopItemColNum());
                        advertiserAdzoneRptsTotalGet.setAvgAccessPageNum(advertiserAdzoneRptsTotalGet.getAvgAccessPageNum() == null ? "0" : advertiserAdzoneRptsTotalGet.getAvgAccessPageNum());
                        advertiserAdzoneRptsTotalGet.setAvgAccessTime(advertiserAdzoneRptsTotalGet.getAvgAccessTime() == null ? "0" : advertiserAdzoneRptsTotalGet.getAvgAccessTime());
                        advertiserAdzoneRptsTotalGet.setDeepInshopUv(advertiserAdzoneRptsTotalGet.getDeepInshopUv() == null ? "0" : advertiserAdzoneRptsTotalGet.getDeepInshopUv());
                        advertiserAdzoneRptsTotalGet.setUv(advertiserAdzoneRptsTotalGet.getUv() == null ? "0" : advertiserAdzoneRptsTotalGet.getUv());
                        advertiserAdzoneRptsTotalGet.setEcpm(advertiserAdzoneRptsTotalGet.getEcpm() == null ? "0" : advertiserAdzoneRptsTotalGet.getEcpm());
                        advertiserAdzoneRptsTotalGet.setEcpc(advertiserAdzoneRptsTotalGet.getEcpc() == null ? "0" : advertiserAdzoneRptsTotalGet.getEcpc());
                        advertiserAdzoneRptsTotalGet.setCtr(advertiserAdzoneRptsTotalGet.getCtr() == null ? "0" : advertiserAdzoneRptsTotalGet.getCtr());
                        advertiserAdzoneRptsTotalGet.setCharge(advertiserAdzoneRptsTotalGet.getCharge() == null ? "0" : advertiserAdzoneRptsTotalGet.getCharge());
                        advertiserAdzoneRptsTotalGet.setClick(advertiserAdzoneRptsTotalGet.getClick() == null ? "0" : advertiserAdzoneRptsTotalGet.getClick());
                        advertiserAdzoneRptsTotalGet.setAdPv(advertiserAdzoneRptsTotalGet.getAdPv() == null ? "0" : advertiserAdzoneRptsTotalGet.getAdPv());
                        advertiserAdzoneRptsTotalGet.setRoi(advertiserAdzoneRptsTotalGet.getRoi() == null ? "0" : advertiserAdzoneRptsTotalGet.getRoi());
                        if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick()) == 0) {
                            advertiserAdzoneRptsTotalGet.setCommodityPurchaseRate("0");//点击量为零
                            advertiserAdzoneRptsTotalGet.setCommodityCollectionRate("0");
                            advertiserAdzoneRptsTotalGet.setTotalCollectionRate("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())));//加购率=添加购物车量/点击量
                            advertiserAdzoneRptsTotalGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())));//收藏率=收藏宝贝量/点击量
                            advertiserAdzoneRptsTotalGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                        }
                        Double collectionAndBuy = Double.parseDouble(advertiserAdzoneRptsTotalGet.getDirShopColNum()) + Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum()) + Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum());
                        if (collectionAndBuy == 0) {
                            advertiserAdzoneRptsTotalGet.setTotalCollectionPlusCost("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge()) / collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum()) == 0) {
                            advertiserAdzoneRptsTotalGet.setCommodityCollectionCost("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum()) == 0) {
                            advertiserAdzoneRptsTotalGet.setCommodityPlusCost("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getCartNum())));//加购成本=消耗/添加购物车量
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getUv()) == 0) {
                            advertiserAdzoneRptsTotalGet.setAverageUvValue("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                        }
                        if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt()) == 0) {
                            advertiserAdzoneRptsTotalGet.setOrderAverageAmount("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInshopAmt()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge()) == 0) {
                            advertiserAdzoneRptsTotalGet.setAverageCostOfOrder("0");
                        } else {
                            advertiserAdzoneRptsTotalGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserAdzoneRptsTotalGet.getCharge()) / Double.parseDouble(advertiserAdzoneRptsTotalGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                        }
                        advertiserAdzoneRptsTotalGet.setTaobaoUserId(user.getTaobaoUserId());
                        advertiserAdzoneRptsTotalGetMapper.insert(advertiserAdzoneRptsTotalGet);
                    }
                    System.out.println("hah");
                }
            }
        }
    }
}

