package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CrowdListMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TargetAdzoneRptsDayGetMapper;
import com.juzuan.advertiser.rpts.model.CrowdList;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TargetAdzoneRptsDayGet;
import com.juzuan.advertiser.rpts.service.TargetAdzoneRptsDayGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserTargetAdzoneRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserTargetAdzoneRptsDayGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 *钻展定向资源位叉乘数据分日列表查询
 */
@Service
public class TargetAdzoneRptsDayGetServiceImpl implements TargetAdzoneRptsDayGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CrowdListMapper crowdListMapper;
    @Autowired
    private TargetAdzoneRptsDayGetMapper targetAdzoneRptsDayGetMapper;
    @Autowired
    private RequestMapper requestMapper;
    //定时更新，每天3:00
    @Scheduled(cron = "0 0 3 * * ?")
    public String TargetAdzoneRptsDayGet(){
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        String time= sdf.format(new java.util.Date());
        //获取系统前一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        String yestime = sdf.format(calendar.getTime());
        List<Request> requests = requestMapper.selectAllRequest();
        for (Request request:requests) {
            List<CrowdList> crowdLists = crowdListMapper.selectAllCrowd();
            //循环发送请求
            for (CrowdList cl : crowdLists) {
                TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cl.getTaobaoUserId());
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserTargetAdzoneRptsDayGetRequest req = new ZuanshiAdvertiserTargetAdzoneRptsDayGetRequest();
                req.setCampaignId(cl.getCampaignId());
                req.setAdgroupId(cl.getAdgroupId());
                req.setAdzoneId(cl.getAdzoneId());
                req.setTargetId(cl.getTargetId());
                req.setEffectType(request.getEffectType());
                req.setEffect(request.getEffect());
                req.setStartTime(yestime);
                req.setEndTime(time);
                ZuanshiAdvertiserTargetAdzoneRptsDayGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("钻展定向资源位叉乘数据分日列表 : " + rsp.getBody());
                //解析json
                JSONObject oneObject = JSON.parseObject(rsp.getBody());
                JSONObject zuanshi = oneObject.getJSONObject("zuanshi_advertiser_target_adzone_rpts_day_get_response");
                JSONObject twoObject = JSON.parseObject(zuanshi.toString());
                JSONObject taradz = twoObject.getJSONObject("target_adzone_offline_rpt_days_list");
                JSONObject threeObject = JSON.parseObject(taradz.toString());
                JSONArray data = threeObject.getJSONArray("data");
                if (data == null) {
                    continue;
                } else {
                    List<TargetAdzoneRptsDayGet> targetAdzoneRptsDayGets = JSONObject.parseArray(data.toString(), TargetAdzoneRptsDayGet.class);
                    for (TargetAdzoneRptsDayGet tard : targetAdzoneRptsDayGets) {
                        tard.setEffect(request.getEffect());
                        tard.setEffectType(request.getEffectType());
                        tard.setTaobaoUserId(cl.getTaobaoUserId());
                        tard.setCampaignId(tard.getCampaignId());
                        tard.setCampaignName(tard.getCampaignName());
                        tard.setAdgroupId(tard.getAdgroupId());
                        tard.setAdgroupName(tard.getAdgroupName());
                        tard.setAdzoneId(tard.getAdzoneId());
                        tard.setAdzoneName(tard.getAdzoneName());
                        tard.setTargetId(tard.getTargetId());
                        tard.setTargetName(tard.getTargetName());
                        tard.setUv(tard.getUv() == null ? "0" : tard.getUv());
                        tard.setAvgAccessTime(tard.getAvgAccessTime() == null ? "0" : tard.getAvgAccessTime());
                        tard.setCharge(tard.getCharge() == null ? "0" : tard.getCharge());
                        tard.setAlipayInshopAmt(tard.getAlipayInshopAmt() == null ? "0" : tard.getAlipayInshopAmt());
                        tard.setAvgAccessPageNum(tard.getAvgAccessPageNum() == null ? "0" : tard.getAvgAccessPageNum());
                        tard.setDirShopColNum(tard.getDirShopColNum() == null ? "0" : tard.getDirShopColNum());
                        tard.setGmvInshopNum(tard.getGmvInshopNum() == null ? "0" : tard.getGmvInshopNum());
                        tard.setClick(tard.getClick() == null ? "0" : tard.getClick());
                        tard.setRoi(tard.getRoi() == null ? "0" : tard.getRoi());
                        tard.setGmvInshopAmt(tard.getGmvInshopAmt() == null ? "0" : tard.getGmvInshopAmt());
                        tard.setCartNum(tard.getCartNum() == null ? "0" : tard.getCartNum());
                        tard.setDeepInshopUv(tard.getDeepInshopUv() == null ? "0" : tard.getDeepInshopUv());
                        tard.setInshopItemColNum(tard.getInshopItemColNum() == null ? "0" : tard.getInshopItemColNum());
                        tard.setCvr(tard.getCvr() == null ? "0" : tard.getCvr());
                        tard.setAlipayInShopNum(tard.getAlipayInShopNum() == null ? "0" : tard.getAlipayInShopNum());
                        tard.setEcpc(tard.getEcpc() == null ? "0" : tard.getEcpc());
                        tard.setEcpm(tard.getEcpm() == null ? "0" : tard.getEcpm());
                        tard.setAdPv(tard.getAdPv() == null ? "0" : tard.getAdPv());
                        tard.setCtr(tard.getCtr() == null ? "0" : tard.getCtr());
                        Double collectionAndBuy = Double.parseDouble(tard.getDirShopColNum()) + Double.parseDouble(tard.getInshopItemColNum()) + Double.parseDouble(tard.getCartNum());
                        if (collectionAndBuy == 0) {
                            tard.setTotalCollectionPlusCost("0");
                            tard.setTotalCollectionRate("0");
                        } else {
                            tard.setTotalCollectionPlusCost(tard.getCharge() == null ? "0" : String.valueOf(Double.parseDouble(tard.getCharge()) / collectionAndBuy));
                            tard.setTotalCollectionRate(tard.getClick() == null ? "0" : String.valueOf(Double.parseDouble(tard.getClick()) / collectionAndBuy));
                        }
                        if (Double.parseDouble(tard.getCharge()) == 0) {
                            tard.setCommodityPurchaseRate("0");
                            tard.setTotalCollectionPlusCost("0");
                        } else {
                            tard.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(tard.getCartNum()) / Double.parseDouble(tard.getCharge())));

                        }
                        if (Double.parseDouble(tard.getClick()) == 0) {
                            tard.setCommodityCollectionRate("0");
                            tard.setTotalCollectionRate("0");
                        } else {
                            tard.setCommodityCollectionRate(String.valueOf(Double.parseDouble(tard.getInshopItemColNum()) / Double.parseDouble(tard.getClick())));

                        }
                        if (Double.parseDouble(tard.getInshopItemColNum()) == 0) {
                            tard.setCommodityCollectionCost("0");
                        } else {
                            tard.setCommodityCollectionCost(String.valueOf(Double.parseDouble(tard.getCharge()) / Double.parseDouble(tard.getInshopItemColNum())));
                        }
                        tard.setCommodityPlusCost(String.valueOf(Double.parseDouble(tard.getCharge() == null ? "0" : tard.getCharge()) + Double.parseDouble(tard.getCartNum() == null ? "0" : tard.getCartNum())));
                        if (Double.parseDouble(tard.getUv()) == 0) {
                            tard.setAverageUvValue("0");
                        } else {
                            tard.setAverageUvValue(String.valueOf(Double.parseDouble(tard.getAlipayInshopAmt()) / Double.parseDouble(tard.getUv())));
                        }
                        if (Double.parseDouble(tard.getAlipayInShopNum()) == 0) {
                            tard.setOrderAverageAmount("0");
                            tard.setAverageCostOfOrder("0");
                        } else {
                            tard.setOrderAverageAmount(String.valueOf(Double.parseDouble(tard.getAlipayInshopAmt()) / Double.parseDouble(tard.getAlipayInShopNum())));
                            tard.setAverageCostOfOrder(String.valueOf(Double.parseDouble(tard.getCharge()) / Double.parseDouble(tard.getAlipayInShopNum())));
                        }
                        targetAdzoneRptsDayGetMapper.insert(tard);
                    }
                }

            }
        }
        return "";
    }
}
