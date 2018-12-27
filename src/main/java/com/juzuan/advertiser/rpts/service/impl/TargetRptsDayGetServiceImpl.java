package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CrowdListMapper;
import com.juzuan.advertiser.rpts.mapper.RequestMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TargetRptsDayGetMapper;
import com.juzuan.advertiser.rpts.model.CrowdList;
import com.juzuan.advertiser.rpts.model.Request;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TargetRptsDayGet;
import com.juzuan.advertiser.rpts.service.TargetRptsDayGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserTargetRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserTargetRptsDayGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 钻展定向数据分日列表
 */
@Service
public class TargetRptsDayGetServiceImpl implements TargetRptsDayGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private TargetRptsDayGetMapper targetRptsDayGetMapper;
    @Autowired
    private CrowdListMapper crowdListMapper;
    @Autowired
    private RequestMapper requestMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String TargetRptsDayGet(){
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
            for (CrowdList cro : crowdLists) {
                TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(cro.getTaobaoUserId());
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserTargetRptsDayGetRequest req = new ZuanshiAdvertiserTargetRptsDayGetRequest();
                req.setStartTime("2018-09-20");
                req.setEndTime("2018-12-20");
                req.setCampaignId(cro.getCampaignId());
                req.setAdgroupId(cro.getAdgroupId());
                req.setTargetId(cro.getTargetId());
                req.setEffect(7L);
                req.setCampaignModel(1L);
                req.setEffectType("impression");
                ZuanshiAdvertiserTargetRptsDayGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("钻展定向数据分日列表 : " + rsp.getBody());
                //"zuanshi_advertiser_target_rpts_day_get_response"
                JSONObject oneObject = JSON.parseObject(rsp.getBody());
                JSONObject atrdgr = oneObject.getJSONObject("zuanshi_advertiser_target_rpts_day_get_response");
                //"target_offline_rpt_days_list"
                JSONObject twoObject = JSON.parseObject(atrdgr.toString());
                JSONObject tordl = twoObject.getJSONObject("target_offline_rpt_days_list");
                //"data":[
                JSONObject thrObject = JSON.parseObject(tordl.toString());
                JSONArray data = thrObject.getJSONArray("data");
                if (data == null) {
                    continue;
                } else {
                    System.out.println("data数组 : " + data.toString());
                    List<TargetRptsDayGet> targetRptsDayGets = JSONObject.parseArray(data.toString(), TargetRptsDayGet.class);
                    for (TargetRptsDayGet trd : targetRptsDayGets) {
                        trd.setTaobaoUserId(cro.getTaobaoUserId());
                        trd.setUv(trd.getUv() == null ? "0" : trd.getUv());
                        trd.setAvgAccessTime(trd.getAvgAccessTime() == null ? "0" : trd.getAvgAccessTime());
                        trd.setCharge(trd.getCharge() == null ? "0" : trd.getCharge());
                        trd.setAlipayInshopAmt(trd.getAlipayInshopAmt() == null ? "0" : trd.getAlipayInshopAmt());
                        trd.setAvgAccessPageNum(trd.getAvgAccessPageNum() == null ? "0" : trd.getAvgAccessPageNum());
                        trd.setDirShopColNum(trd.getDirShopColNum() == null ? "0" : trd.getDirShopColNum());
                        trd.setGmvInshopNum(trd.getGmvInshopNum() == null ? "0" : trd.getGmvInshopNum());
                        trd.setClick(trd.getClick() == null ? "0" : trd.getClick());
                        trd.setRoi(trd.getRoi() == null ? "0" : trd.getRoi());
                        trd.setGmvInshopAmt(trd.getGmvInshopAmt() == null ? "0" : trd.getGmvInshopAmt());
                        trd.setCartNum(trd.getCartNum() == null ? "0" : trd.getCartNum());
                        trd.setDeepInshopUv(trd.getDeepInshopUv() == null ? "0" : trd.getDeepInshopUv());
                        trd.setInshopItemColNum(trd.getInshopItemColNum() == null ? "0" : trd.getInshopItemColNum());
                        trd.setCvr(trd.getCvr() == null ? "0" : trd.getCvr());
                        trd.setAlipayInShopNum(trd.getAlipayInShopNum() == null ? "0" : trd.getAlipayInShopNum());
                        trd.setEcpc(trd.getEcpc() == null ? "0" : trd.getEcpc());
                        trd.setEcpm(trd.getEcpm() == null ? "0" : trd.getEcpm());
                        trd.setAdPv(trd.getAdPv() == null ? "0" : trd.getAdPv());
                        trd.setCtr(trd.getCtr() == null ? "0" : trd.getCtr());
                        Double collectionAndBuy = Double.parseDouble(trd.getDirShopColNum()) + Double.parseDouble(trd.getInshopItemColNum()) + Double.parseDouble(trd.getCartNum());
                        if (collectionAndBuy == 0) {
                            trd.setTotalCollectionPlusCost("0");
                            trd.setTotalCollectionRate("0");
                        } else {
                            trd.setTotalCollectionPlusCost(trd.getCharge() == null ? "0" : String.valueOf(Double.parseDouble(trd.getCharge()) / collectionAndBuy));
                            trd.setTotalCollectionRate(trd.getClick() == null ? "0" : String.valueOf(Double.parseDouble(trd.getClick()) / collectionAndBuy));
                        }
                        if (Double.parseDouble(trd.getCharge()) == 0) {
                            trd.setCommodityPurchaseRate("0");
                            trd.setTotalCollectionPlusCost("0");
                        } else {
                            trd.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(trd.getCartNum()) / Double.parseDouble(trd.getCharge())));

                        }
                        if (Double.parseDouble(trd.getClick()) == 0) {
                            trd.setCommodityCollectionRate("0");
                            trd.setTotalCollectionRate("0");
                        } else {
                            trd.setCommodityCollectionRate(String.valueOf(Double.parseDouble(trd.getInshopItemColNum()) / Double.parseDouble(trd.getClick())));

                        }
                        if (Double.parseDouble(trd.getInshopItemColNum()) == 0) {
                            trd.setCommodityCollectionCost("0");
                        } else {
                            trd.setCommodityCollectionCost(String.valueOf(Double.parseDouble(trd.getCharge()) / Double.parseDouble(trd.getInshopItemColNum())));
                        }
                        trd.setCommodityPlusCost(String.valueOf(Double.parseDouble(trd.getCharge() == null ? "0" : trd.getCharge()) + Double.parseDouble(trd.getCartNum() == null ? "0" : trd.getCartNum())));
                        if (Double.parseDouble(trd.getUv()) == 0) {
                            trd.setAverageUvValue("0");
                        } else {
                            trd.setAverageUvValue(String.valueOf(Double.parseDouble(trd.getAlipayInshopAmt()) / Double.parseDouble(trd.getUv())));
                        }
                        if (Double.parseDouble(trd.getAlipayInShopNum()) == 0) {
                            trd.setOrderAverageAmount("0");
                            trd.setAverageCostOfOrder("0");
                        } else {
                            trd.setOrderAverageAmount(String.valueOf(Double.parseDouble(trd.getAlipayInshopAmt()) / Double.parseDouble(trd.getAlipayInShopNum())));
                            trd.setAverageCostOfOrder(String.valueOf(Double.parseDouble(trd.getCharge()) / Double.parseDouble(trd.getAlipayInShopNum())));
                        }
                        targetRptsDayGetMapper.insert(trd);
                    }
                }
            }
        }
        return "";
    }
}
