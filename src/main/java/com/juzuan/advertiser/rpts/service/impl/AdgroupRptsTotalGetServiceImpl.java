package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.AdgroupRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.AdgroupRptsTotalGet;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdgroupRptsTotalGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAdgroupRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAdgroupRptsTotalGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 钻展广告主推广单元多日汇总数据查询
 */
@Service
public class AdgroupRptsTotalGetServiceImpl implements AdgroupRptsTotalGetService {
    private static String appkey = "25139411";
    private static String url = "https://eco.taobao.com/router/rest";
    private static String secret = "ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private AdgroupRptsTotalGetMapper adgroupRptsTotalGetMapper;
    @Autowired
    private RequestMapper requestMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String AdgroupRptsTotalGet(){
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
            List<AdgroupList> adgroupRptsTotalGets = adgroupListMapper.selectAllAdgroup();
            for (AdgroupList al : adgroupRptsTotalGets) {
                TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(al.getTaobaoUserId());
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserAdgroupRptsTotalGetRequest req = new ZuanshiAdvertiserAdgroupRptsTotalGetRequest();
                req.setStartTime("2018-08-29");
                req.setEndTime("2018-11-30");
                req.setEffect(request.getEffect());
                req.setCampaignModel(request.getCampaignModel());
                req.setEffectType(request.getEffectType());
                req.setPageSize(200L);
                req.setOffset(0L);
                ZuanshiAdvertiserAdgroupRptsTotalGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("推广单元多日汇总数据json: " + rsp.getBody());
                JSONObject oneObject = JSON.parseObject(rsp.getBody());
                JSONObject zuanshi = oneObject.getJSONObject("zuanshi_advertiser_adgroup_rpts_total_get_response");
                JSONObject twoObject = JSON.parseObject(zuanshi.toString());
                JSONObject list = twoObject.getJSONObject("adgroup_offline_rpt_total_list");
                JSONObject thrObject = JSON.parseObject(list.toString());
                JSONArray data = thrObject.getJSONArray("data");
                if (data == null) {
                    continue;
                } else {
                    List<AdgroupRptsTotalGet> adgroupRptsTotalGet = JSONObject.parseArray(data.toString(), AdgroupRptsTotalGet.class);
                    for (AdgroupRptsTotalGet artg : adgroupRptsTotalGet) {
                        artg.setTaobaoUserId(al.getTaobaoUserId());
                        artg.setEffect(request.getEffect());
                        artg.setCampaignModel(request.getCampaignModel());
                        artg.setEffectType(request.getEffectType());
                        artg.setUv(artg.getUv() == null ? "0" : artg.getUv());
                        artg.setAvgAccessTime(artg.getAvgAccessTime() == null ? "0" : artg.getAvgAccessTime());
                        artg.setCharge(artg.getCharge() == null ? "0" : artg.getCharge());
                        artg.setAlipayInshopAmt(artg.getAlipayInshopAmt() == null ? "0" : artg.getAlipayInshopAmt());
                        artg.setAvgAccessPageNum(artg.getAvgAccessPageNum() == null ? "0" : artg.getAvgAccessPageNum());
                        artg.setDirShopColNum(artg.getDirShopColNum() == null ? "0" : artg.getDirShopColNum());
                        artg.setGmvInshopNum(artg.getGmvInshopNum() == null ? "0" : artg.getGmvInshopNum());
                        artg.setClick(artg.getClick() == null ? "0" : artg.getClick());
                        artg.setRoi(artg.getRoi() == null ? "0" : artg.getRoi());
                        artg.setGmvInshopAmt(artg.getGmvInshopAmt() == null ? "0" : artg.getGmvInshopAmt());
                        artg.setCartNum(artg.getCartNum() == null ? "0" : artg.getCartNum());
                        artg.setDeepInshopUv(artg.getDeepInshopUv() == null ? "0" : artg.getDeepInshopUv());
                        artg.setInshopItemColNum(artg.getInshopItemColNum() == null ? "0" : artg.getInshopItemColNum());
                        artg.setCvr(artg.getCvr() == null ? "0" : artg.getCvr());
                        artg.setAlipayInShopNum(artg.getAlipayInShopNum() == null ? "0" : artg.getAlipayInShopNum());
                        artg.setEcpc(artg.getEcpc() == null ? "0" : artg.getEcpc());
                        artg.setEcpm(artg.getEcpm() == null ? "0" : artg.getEcpm());
                        artg.setAdPv(artg.getAdPv() == null ? "0" : artg.getAdPv());
                        artg.setCtr(artg.getCtr() == null ? "0" : artg.getCtr());
                        Double collectionAndBuy = Double.parseDouble(artg.getDirShopColNum()) + Double.parseDouble(artg.getInshopItemColNum()) + Double.parseDouble(artg.getCartNum());
                        if (collectionAndBuy == 0) {
                            artg.setTotalCollectionPlusCost("0");
                            artg.setTotalCollectionRate("0");
                        } else {
                            artg.setTotalCollectionPlusCost(artg.getCharge() == null ? "0" : String.valueOf(Double.parseDouble(artg.getCharge()) / collectionAndBuy));
                            artg.setTotalCollectionRate(artg.getClick() == null ? "0" : String.valueOf(Double.parseDouble(artg.getClick()) / collectionAndBuy));
                        }
                        if (Double.parseDouble(artg.getCharge()) == 0) {
                            artg.setCommodityPurchaseRate("0");
                            artg.setTotalCollectionPlusCost("0");
                        } else {
                            artg.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(artg.getCartNum()) / Double.parseDouble(artg.getCharge())));
                        }
                        if (Double.parseDouble(artg.getClick()) == 0) {
                            artg.setCommodityCollectionRate("0");
                            artg.setTotalCollectionRate("0");
                        } else {
                            artg.setCommodityCollectionRate(String.valueOf(Double.parseDouble(artg.getInshopItemColNum()) / Double.parseDouble(artg.getClick())));
                        }
                        if (Double.parseDouble(artg.getInshopItemColNum()) == 0) {
                            artg.setCommodityCollectionCost("0");
                        } else {
                            artg.setCommodityCollectionCost(String.valueOf(Double.parseDouble(artg.getCharge()) / Double.parseDouble(artg.getInshopItemColNum())));
                        }
                        artg.setCommodityPlusCost(String.valueOf(Double.parseDouble(artg.getCharge() == null ? "0" : artg.getCharge()) + Double.parseDouble(artg.getCartNum() == null ? "0" : artg.getCartNum())));
                        if (Double.parseDouble(artg.getUv()) == 0) {
                            artg.setAverageUvValue("0");
                        } else {
                            artg.setAverageUvValue(String.valueOf(Double.parseDouble(artg.getAlipayInshopAmt()) / Double.parseDouble(artg.getUv())));
                        }
                        if (Double.parseDouble(artg.getAlipayInShopNum()) == 0) {
                            artg.setOrderAverageAmount("0");
                            artg.setAverageCostOfOrder("0");
                        } else {
                            artg.setOrderAverageAmount(String.valueOf(Double.parseDouble(artg.getAlipayInshopAmt()) / Double.parseDouble(artg.getAlipayInShopNum())));
                            artg.setAverageCostOfOrder(String.valueOf(Double.parseDouble(artg.getCharge()) / Double.parseDouble(artg.getAlipayInShopNum())));
                        }
                        adgroupRptsTotalGetMapper.insert(artg);
                    }
                }

            }
        }
        return "";
    }

}
