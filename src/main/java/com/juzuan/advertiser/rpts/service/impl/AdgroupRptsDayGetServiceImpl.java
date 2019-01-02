package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.AdgroupRptsDayGetMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.AdgroupRptsDayGet;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdgroupRptsDayGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAdgroupRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAdgroupRptsDayGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 钻展推广单元数据分日列表详
 */
@Service
public class AdgroupRptsDayGetServiceImpl implements AdgroupRptsDayGetService {
    private static String appkey = "25139411";
    private static String url = "https://eco.taobao.com/router/rest";
    private static String secret = "ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private AdgroupRptsDayGetMapper adgroupRptsDayGetMapper;
    @Autowired
    private RequestMapper requestMapper;
    //定时更新，每天3:00
    @Scheduled(cron = "0 0 3 * * ?")
    public String AdgroupRptsDayGet() throws ApiException {
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
            List<AdgroupList> cam = adgroupListMapper.selectAllAdgroup();
            for (AdgroupList ad : cam) {
                TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(ad.getTaobaoUserId());
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserAdgroupRptsDayGetRequest req = new ZuanshiAdvertiserAdgroupRptsDayGetRequest();
                //req.setStartTime("2018-11-06");
                req.setStartTime(yestime);
                //req.setEndTime("2018-11-12");
                req.setEndTime(time);
                req.setCampaignId(ad.getCampaignId());
                req.setAdgroupId(ad.getAdgroupId());
                req.setEffect(request.getEffect());
                req.setCampaignModel(request.getCampaignModel());
                req.setEffectType(request.getEffectType());
                ZuanshiAdvertiserAdgroupRptsDayGetResponse rsp = client.execute(req, sessionKey);
                System.out.println("钻展推广单元数据分日列表详情 : " + rsp.getBody());
                //解析外层json
                JSONObject oneObject = JSON.parseObject(rsp.getBody());
                JSONObject twoObject = oneObject.getJSONObject("zuanshi_advertiser_adgroup_rpts_day_get_response");
                System.out.println("外层json : " + twoObject.toString());
                //解析里层json
                JSONObject thrObject = JSON.parseObject(twoObject.toString());
                JSONObject fouObject = thrObject.getJSONObject("adgroup_offline_rpt_days_list");
                System.out.println("里层json : " + fouObject.toString());
                //解析data数组
                JSONObject fivObject = JSON.parseObject(fouObject.toString());
                JSONArray data = fivObject.getJSONArray("data");
                if (data == null) {
                    continue;
                } else {
                    System.out.println("data数组 : " + data.toString());
                    List<AdgroupRptsDayGet> adgroupRptsDayGets = JSONObject.parseArray(data.toString(), AdgroupRptsDayGet.class);
                    //先遍历
                    for (AdgroupRptsDayGet ard : adgroupRptsDayGets) {
                        ard.setTaobaoUserId(ad.getTaobaoUserId());
                        ard.setUv(ard.getUv() == null ? "0" : ard.getUv());
                        ard.setAvgAccessTime(ard.getAvgAccessTime() == null ? "0" : ard.getAvgAccessTime());
                        ard.setCharge(ard.getCharge() == null ? "0" : ard.getCharge());
                        ard.setAlipayInshopAmt(ard.getAlipayInshopAmt() == null ? "0" : ard.getAlipayInshopAmt());
                        ard.setAvgAccessPageNum(ard.getAvgAccessPageNum() == null ? "0" : ard.getAvgAccessPageNum());
                        ard.setDirShopColNum(ard.getDirShopColNum() == null ? "0" : ard.getDirShopColNum());
                        ard.setGmvInshopNum(ard.getGmvInshopNum() == null ? "0" : ard.getGmvInshopNum());
                        ard.setClick(ard.getClick() == null ? "0" : ard.getClick());
                        ard.setRoi(ard.getRoi() == null ? "0" : ard.getRoi());
                        ard.setGmvInshopAmt(ard.getGmvInshopAmt() == null ? "0" : ard.getGmvInshopAmt());
                        ard.setCartNum(ard.getCartNum() == null ? "0" : ard.getCartNum());
                        ard.setDeepInshopUv(ard.getDeepInshopUv() == null ? "0" : ard.getDeepInshopUv());
                        ard.setInshopItemColNum(ard.getInshopItemColNum() == null ? "0" : ard.getInshopItemColNum());
                        ard.setCvr(ard.getCvr() == null ? "0" : ard.getCvr());
                        ard.setAlipayInShopNum(ard.getAlipayInShopNum() == null ? "0" : ard.getAlipayInShopNum());
                        ard.setEcpc(ard.getEcpc() == null ? "0" : ard.getEcpc());
                        ard.setEcpm(ard.getEcpm() == null ? "0" : ard.getEcpm());
                        ard.setAdPv(ard.getAdPv() == null ? "0" : ard.getAdPv());
                        ard.setCtr(ard.getCtr() == null ? "0" : ard.getCtr());
                        ard.setEffect(request.getEffect());
                        ard.setCampaignModel(request.getCampaignModel());
                        ard.setEffectType(request.getEffectType());
                        Double collectionAndBuy = Double.parseDouble(ard.getDirShopColNum()) + Double.parseDouble(ard.getInshopItemColNum()) + Double.parseDouble(ard.getCartNum());
                        if (collectionAndBuy == 0) {
                            ard.setTotalCollectionPlusCost("0");
                            ard.setTotalCollectionRate("0");
                        } else {
                            ard.setTotalCollectionPlusCost(ard.getCharge() == null ? "0" : String.valueOf(Double.parseDouble(ard.getCharge()) / collectionAndBuy));
                            ard.setTotalCollectionRate(ard.getClick() == null ? "0" : String.valueOf(Double.parseDouble(ard.getClick()) / collectionAndBuy));
                        }
                        if (Double.parseDouble(ard.getCharge()) == 0) {
                            ard.setCommodityPurchaseRate("0");
                            ard.setTotalCollectionPlusCost("0");
                        } else {
                            ard.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(ard.getCartNum()) / Double.parseDouble(ard.getCharge())));
                        }
                        if (Double.parseDouble(ard.getClick()) == 0) {
                            ard.setCommodityCollectionRate("0");
                            ard.setTotalCollectionRate("0");
                        } else {
                            ard.setCommodityCollectionRate(String.valueOf(Double.parseDouble(ard.getInshopItemColNum()) / Double.parseDouble(ard.getClick())));
                        }
                        if (Double.parseDouble(ard.getInshopItemColNum()) == 0) {
                            ard.setCommodityCollectionCost("0");
                        } else {
                            ard.setCommodityCollectionCost(String.valueOf(Double.parseDouble(ard.getCharge()) / Double.parseDouble(ard.getInshopItemColNum())));
                        }
                        ard.setCommodityPlusCost(String.valueOf(Double.parseDouble(ard.getCharge() == null ? "0" : ard.getCharge()) + Double.parseDouble(ard.getCartNum() == null ? "0" : ard.getCartNum())));
                        if (Double.parseDouble(ard.getUv()) == 0) {
                            ard.setAverageUvValue("0");
                        } else {
                            ard.setAverageUvValue(String.valueOf(Double.parseDouble(ard.getAlipayInshopAmt()) / Double.parseDouble(ard.getUv())));
                        }
                        if (Double.parseDouble(ard.getAlipayInShopNum()) == 0) {
                            ard.setOrderAverageAmount("0");
                            ard.setAverageCostOfOrder("0");
                        } else {
                            ard.setOrderAverageAmount(String.valueOf(Double.parseDouble(ard.getAlipayInshopAmt()) / Double.parseDouble(ard.getAlipayInShopNum())));
                            ard.setAverageCostOfOrder(String.valueOf(Double.parseDouble(ard.getCharge()) / Double.parseDouble(ard.getAlipayInShopNum())));
                        }
                        adgroupRptsDayGetMapper.insert(ard);
                    }
                }
            }
        }
        return "";
    }

    @Override
    public void parseAdgroupRptsDay(String json){

    }
}

