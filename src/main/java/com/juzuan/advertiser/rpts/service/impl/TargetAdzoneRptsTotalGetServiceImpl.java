package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TargetAdzoneRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TargetAdzoneRptsTotalGet;
import com.juzuan.advertiser.rpts.service.TargetAdzoneRptsTotalGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserTargetAdzoneRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserTargetAdzoneRptsTotalGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 钻展定向-资源位多日数据汇总查询
 */
@Service
public class TargetAdzoneRptsTotalGetServiceImpl implements TargetAdzoneRptsTotalGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private TargetAdzoneRptsTotalGetMapper targetAdzoneRptsTotalGetMapper;
    @Autowired
    private RequestMapper requestMapper;
    @Scheduled(cron = "0 20 3 1/7 * ?")
    public String TargetAdzoneRptsTotalGet(){
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        String  time= sdf.format(new java.util.Date());
        System.out.println(time);
        //获取系统前一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-7);
        String  yestime = sdf.format(calendar.getTime());
        System.out.println(yestime);
        List<Request> requests = requestMapper.selectAllRequest();
        for (Request request:requests) {
            List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
            for (TaobaoAuthorizeUser tau : taobaoAuthorizeUsers) {
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserTargetAdzoneRptsTotalGetRequest req = new ZuanshiAdvertiserTargetAdzoneRptsTotalGetRequest();
                req.setEffectType(request.getEffectType());
                req.setEffect(request.getEffect());
                req.setStartTime(yestime);
                req.setEndTime(time);
                req.setPageSize(200L);
                req.setOffset(0L);
                ZuanshiAdvertiserTargetAdzoneRptsTotalGetResponse rsp = null;
                try {
                    rsp = client.execute(req, tau.getAccessToken());
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("钻展定向-资源位多日数据汇总查询: " + rsp.getBody());
                JSONObject oneObject = JSON.parseObject(rsp.getBody());
                JSONObject zuanshi = oneObject.getJSONObject("zuanshi_advertiser_target_adzone_rpts_total_get_response");
                JSONObject twoObject = JSON.parseObject(zuanshi.toString());
                JSONObject total = twoObject.getJSONObject("target_adzone_offline_rpt_total_list");
                JSONObject thrObject = JSON.parseObject(total.toString());
                JSONArray data = thrObject.getJSONArray("data");
                if (data == null) {
                    continue;
                } else {
                    List<TargetAdzoneRptsTotalGet> targetAdzoneRptsTotalGets = JSONObject.parseArray(data.toString(), TargetAdzoneRptsTotalGet.class);
                    for (TargetAdzoneRptsTotalGet targ : targetAdzoneRptsTotalGets) {
                        targ.setTaobaoUserId(tau.getTaobaoUserId());
                        targ.setEffect(request.getEffect());
                        targ.setEffectType(request.getEffectType());
                        targ.setUv(targ.getUv() == null ? "0" : targ.getUv());
                        targ.setAvgAccessTime(targ.getAvgAccessTime() == null ? "0" : targ.getAvgAccessTime());
                        targ.setCharge(targ.getCharge() == null ? "0" : targ.getCharge());
                        targ.setAlipayInshopAmt(targ.getAlipayInshopAmt() == null ? "0" : targ.getAlipayInshopAmt());
                        targ.setAvgAccessPageNum(targ.getAvgAccessPageNum() == null ? "0" : targ.getAvgAccessPageNum());
                        targ.setDirShopColNum(targ.getDirShopColNum() == null ? "0" : targ.getDirShopColNum());
                        targ.setGmvInshopNum(targ.getGmvInshopNum() == null ? "0" : targ.getGmvInshopNum());
                        targ.setClick(targ.getClick() == null ? "0" : targ.getClick());
                        targ.setRoi(targ.getRoi() == null ? "0" : targ.getRoi());
                        targ.setGmvInshopAmt(targ.getGmvInshopAmt() == null ? "0" : targ.getGmvInshopAmt());
                        targ.setCartNum(targ.getCartNum() == null ? "0" : targ.getCartNum());
                        targ.setDeepInshopUv(targ.getDeepInshopUv() == null ? "0" : targ.getDeepInshopUv());
                        targ.setInshopItemColNum(targ.getInshopItemColNum() == null ? "0" : targ.getInshopItemColNum());
                        targ.setCvr(targ.getCvr() == null ? "0" : targ.getCvr());
                        targ.setAlipayInShopNum(targ.getAlipayInShopNum() == null ? "0" : targ.getAlipayInShopNum());
                        targ.setEcpc(targ.getEcpc() == null ? "0" : targ.getEcpc());
                        targ.setEcpm(targ.getEcpm() == null ? "0" : targ.getEcpm());
                        targ.setAdPv(targ.getAdPv() == null ? "0" : targ.getAdPv());
                        targ.setCtr(targ.getCtr() == null ? "0" : targ.getCtr());
                        Double collectionAndBuy = Double.parseDouble(targ.getDirShopColNum()) + Double.parseDouble(targ.getInshopItemColNum()) + Double.parseDouble(targ.getCartNum());
                        if (collectionAndBuy == 0) {
                            targ.setTotalCollectionPlusCost("0");
                            targ.setTotalCollectionRate("0");
                        } else {
                            targ.setTotalCollectionPlusCost(targ.getCharge() == null ? "0" : String.valueOf(Double.parseDouble(targ.getCharge()) / collectionAndBuy));
                            targ.setTotalCollectionRate(targ.getClick() == null ? "0" : String.valueOf(Double.parseDouble(targ.getClick()) / collectionAndBuy));
                        }
                        if (Double.parseDouble(targ.getCharge()) == 0) {
                            targ.setCommodityPurchaseRate("0");
                            targ.setTotalCollectionPlusCost("0");
                        } else {
                            targ.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(targ.getCartNum()) / Double.parseDouble(targ.getCharge())));

                        }
                        if (Double.parseDouble(targ.getClick()) == 0) {
                            targ.setCommodityCollectionRate("0");
                            targ.setTotalCollectionRate("0");
                        } else {
                            targ.setCommodityCollectionRate(String.valueOf(Double.parseDouble(targ.getInshopItemColNum()) / Double.parseDouble(targ.getClick())));

                        }
                        if (Double.parseDouble(targ.getInshopItemColNum()) == 0) {
                            targ.setCommodityCollectionCost("0");
                        } else {
                            targ.setCommodityCollectionCost(String.valueOf(Double.parseDouble(targ.getCharge()) / Double.parseDouble(targ.getInshopItemColNum())));
                        }
                        targ.setCommodityPlusCost(String.valueOf(Double.parseDouble(targ.getCharge() == null ? "0" : targ.getCharge()) + Double.parseDouble(targ.getCartNum() == null ? "0" : targ.getCartNum())));
                        if (Double.parseDouble(targ.getUv()) == 0) {
                            targ.setAverageUvValue("0");
                        } else {
                            targ.setAverageUvValue(String.valueOf(Double.parseDouble(targ.getAlipayInshopAmt()) / Double.parseDouble(targ.getUv())));
                        }
                        if (Double.parseDouble(targ.getAlipayInShopNum()) == 0) {
                            targ.setOrderAverageAmount("0");
                            targ.setAverageCostOfOrder("0");
                        } else {
                            targ.setOrderAverageAmount(String.valueOf(Double.parseDouble(targ.getAlipayInshopAmt()) / Double.parseDouble(targ.getAlipayInShopNum())));
                            targ.setAverageCostOfOrder(String.valueOf(Double.parseDouble(targ.getCharge()) / Double.parseDouble(targ.getAlipayInShopNum())));
                        }
                        targetAdzoneRptsTotalGetMapper.insert(targ);
                    }
                }
            }
        }
        return "";
    }

}
