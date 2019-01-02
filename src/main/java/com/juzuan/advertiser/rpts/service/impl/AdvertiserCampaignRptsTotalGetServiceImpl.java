package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserCampaignRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.*;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserCampaignRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserCampaignRptsTotalGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
@Service
public class AdvertiserCampaignRptsTotalGetServiceImpl {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private AdvertiserCampaignRptsTotalGetMapper advertiserCampaignRptsTotalGetMapper;
    //定时更新：每周周日3:00
    @Scheduled(cron = "0 0 3 1/7 * ?")
    public String parseCampaign(){
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        String time= sdf.format(new java.util.Date());
        System.out.println(time);
        //获取系统前一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-7);
        String yestime = sdf.format(calendar.getTime());
        List<Request> requests = requestMapper.selectAllRequest();
        for (Request request:requests) {
            List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
            for (TaobaoAuthorizeUser taobaoAuthorizeUser : taobaoAuthorizeUsers) {
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserCampaignRptsTotalGetRequest req = new ZuanshiAdvertiserCampaignRptsTotalGetRequest();
                req.setStartTime(yestime);
                req.setEndTime(time);
                req.setEffect(request.getEffect());
                req.setCampaignModel(request.getCampaignModel());
                req.setEffectType(request.getEffectType());
                req.setPageSize(200L);
                req.setOffset(0L);
                ZuanshiAdvertiserCampaignRptsTotalGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println(rsp.getBody());
                JSONObject one = JSON.parseObject(rsp.getBody());
                JSONObject onee = one.getJSONObject("zuanshi_advertiser_campaign_rpts_total_get_response");
                // System.out.println(taobaoAuthorizeUser.getTaobaoUserId()+"  "+campaign.getCampaignId() +"  "+onee.toString());
                JSONObject two = JSON.parseObject(onee.toString());
                JSONObject tw = two.getJSONObject("campaign_offline_rpt_total_list");
                if (tw.size() != 0) {
                    JSONArray three = tw.getJSONArray("data");

                    for (Object ob : three.toArray()) {
                        System.out.println("遍历目标数组" + ob.toString());
                    }
                    List<AdvertiserCampaignRptsTotalGet> advertiserCampaignRptsTotalGets = JSONObject.parseArray(three.toString(), AdvertiserCampaignRptsTotalGet.class);
                    //遍历json数据属性对象
                    for (AdvertiserCampaignRptsTotalGet advertiserCampaignRptsTotalGet : advertiserCampaignRptsTotalGets) {
                        System.out.println("遍历对象数组  " + advertiserCampaignRptsTotalGet.toString());
                        //插入计算的属性值
                        if (advertiserCampaignRptsTotalGet.getAdPv() == null) {
                            advertiserCampaignRptsTotalGet.setAdPv("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getAlipayInshopAmt() == null) {
                            advertiserCampaignRptsTotalGet.setAlipayInshopAmt("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getAlipayInShopNum() == null) {
                            advertiserCampaignRptsTotalGet.setAlipayInShopNum("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getAvgAccessPageNum() == null) {
                            advertiserCampaignRptsTotalGet.setAvgAccessPageNum("0");
                        }

                        if (advertiserCampaignRptsTotalGet.getCampaignId() == null) {
                            advertiserCampaignRptsTotalGet.setCampaignId("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getCampaignName() == null) {
                            advertiserCampaignRptsTotalGet.setCampaignName("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getCartNum() == null) {
                            advertiserCampaignRptsTotalGet.setCartNum("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getAvgAccessTime() == null) {
                            advertiserCampaignRptsTotalGet.setAvgAccessTime("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getCharge() == null) {
                            advertiserCampaignRptsTotalGet.setCharge("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getClick() == null) {
                            advertiserCampaignRptsTotalGet.setClick("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getCtr() == null) {
                            advertiserCampaignRptsTotalGet.setCtr("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getCvr() == null) {
                            advertiserCampaignRptsTotalGet.setCvr("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getDeepInshopUv() == null) {
                            advertiserCampaignRptsTotalGet.setDeepInshopUv("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getUv() == null) {
                            advertiserCampaignRptsTotalGet.setUv("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getRoi() == null) {
                            advertiserCampaignRptsTotalGet.setRoi("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getDirShopColNum() == null) {
                            advertiserCampaignRptsTotalGet.setDirShopColNum("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getEcpc() == null) {
                            advertiserCampaignRptsTotalGet.setEcpc("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getEcpm() == null) {
                            advertiserCampaignRptsTotalGet.setEcpm("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getGmvInshopAmt() == null) {
                            advertiserCampaignRptsTotalGet.setGmvInshopAmt("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getGmvInshopNum() == null) {
                            advertiserCampaignRptsTotalGet.setGmvInshopNum("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getCartNum() == null) {
                            advertiserCampaignRptsTotalGet.setCartNum("0");
                        }
                        if (advertiserCampaignRptsTotalGet.getInshopItemColNum() == null) {
                            advertiserCampaignRptsTotalGet.setInshopItemColNum("0");
                        }
                        advertiserCampaignRptsTotalGet.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                        advertiserCampaignRptsTotalGet.setEffect(request.getEffect());
                        advertiserCampaignRptsTotalGet.setEffectType(request.getEffectType());
                        advertiserCampaignRptsTotalGet.setCampaignModel(request.getCampaignModel());
                        if (Double.parseDouble(advertiserCampaignRptsTotalGet.getClick()) == 0) {
                            advertiserCampaignRptsTotalGet.setCommodityPurchaseRate("0");//点击量为零
                            advertiserCampaignRptsTotalGet.setCommodityCollectionRate("0");
                            advertiserCampaignRptsTotalGet.setTotalCollectionRate("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())));//加购率=添加购物车量/点击量
                            advertiserCampaignRptsTotalGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())));//收藏率=收藏宝贝量/点击量
                            advertiserCampaignRptsTotalGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getClick()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                        }
                        Double collectionAndBuy = Double.parseDouble(advertiserCampaignRptsTotalGet.getDirShopColNum()) + Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum()) + Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum());
                        if (collectionAndBuy == 0) {
                            advertiserCampaignRptsTotalGet.setTotalCollectionPlusCost("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge()) / collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                        }
                        if (Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum()) == 0) {
                            advertiserCampaignRptsTotalGet.setCommodityCollectionCost("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                        }
                        if (Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum()) == 0) {
                            advertiserCampaignRptsTotalGet.setCommodityPlusCost("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum())));//加购成本=消耗/添加购物车量
                        }
                        if (Double.parseDouble(advertiserCampaignRptsTotalGet.getUv()) == 0) {
                            advertiserCampaignRptsTotalGet.setAverageUvValue("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInshopAmt()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                        }
                        if (Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInshopAmt()) == 0) {
                            advertiserCampaignRptsTotalGet.setOrderAverageAmount("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInshopAmt()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                        }
                        if (Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge()) == 0) {
                            advertiserCampaignRptsTotalGet.setAverageCostOfOrder("0");
                        } else {
                            advertiserCampaignRptsTotalGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge()) / Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                        }
                    }
                } else {
                    System.out.println("没有获取的信息");
                    continue;
                }
            }
        }
        return "";
    }
}
