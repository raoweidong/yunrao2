package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TargetRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.model.TargetRptsTotalGet;
import com.juzuan.advertiser.rpts.service.TargetRptsTotalGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserTargetAdzoneRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserTargetAdzoneRptsTotalGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钻展定向-资源位多日数据汇总
 */
@Service
public class TargetRptsTotalGetServiceImpl implements TargetRptsTotalGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private TargetRptsTotalGetMapper targetRptsTotalGetMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String TargetRptsTotalGet(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau:taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiAdvertiserTargetAdzoneRptsTotalGetRequest req = new ZuanshiAdvertiserTargetAdzoneRptsTotalGetRequest();
            req.setEffectType("click");
            req.setEffect(7L);
            req.setStartTime("2018-08-01");
            req.setEndTime("2018-11-30");
            req.setPageSize(200L);
            req.setOffset(0L);
            ZuanshiAdvertiserTargetAdzoneRptsTotalGetResponse rsp = null;
            try{
                rsp = client.execute(req,tau.getAccessToken());
            }catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("钻展定向-资源位多日数据汇总: "+rsp.getBody());
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_advertiser_target_adzone_rpts_total_get_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject list = twoObject.getJSONObject("target_adzone_offline_rpt_total_list");
            JSONObject thrObject = JSON.parseObject(list.toString());
            JSONArray data = thrObject.getJSONArray("data");
            if (data == null){
                continue;
            }else {
                List<TargetRptsTotalGet> targetRptsTotalGets = JSONObject.parseArray(data.toString(),TargetRptsTotalGet.class);
                for (TargetRptsTotalGet trtg:targetRptsTotalGets) {
                    trtg.setTaobaoUserId(tau.getTaobaoUserId());
                    trtg.setUv(trtg.getUv()==null?"0":trtg.getUv());
                    trtg.setAvgAccessTime(trtg.getAvgAccessTime()==null?"0":trtg.getAvgAccessTime());
                    trtg.setCharge(trtg.getCharge()==null?"0":trtg.getCharge());
                    trtg.setAlipayInshopAmt(trtg.getAlipayInshopAmt()==null?"0":trtg.getAlipayInshopAmt());
                    trtg.setAvgAccessPageNum(trtg.getAvgAccessPageNum()==null?"0":trtg.getAvgAccessPageNum());
                    trtg.setDirShopColNum(trtg.getDirShopColNum()==null?"0":trtg.getDirShopColNum());
                    trtg.setGmvInshopNum(trtg.getGmvInshopNum()==null?"0":trtg.getGmvInshopNum());
                    trtg.setClick(trtg.getClick()==null?"0":trtg.getClick());
                    trtg.setRoi(trtg.getRoi()==null?"0":trtg.getRoi());
                    trtg.setGmvInshopAmt(trtg.getGmvInshopAmt()==null?"0":trtg.getGmvInshopAmt());
                    trtg.setCartNum(trtg.getCartNum()==null?"0":trtg.getCartNum());
                    trtg.setDeepInshopUv(trtg.getDeepInshopUv()==null?"0":trtg.getDeepInshopUv());
                    trtg.setInshopItemColNum(trtg.getInshopItemColNum()==null?"0":trtg.getInshopItemColNum());
                    trtg.setCvr(trtg.getCvr()==null?"0":trtg.getCvr());
                    trtg.setAlipayInShopNum(trtg.getAlipayInShopNum()==null?"0":trtg.getAlipayInShopNum());
                    trtg.setEcpc(trtg.getEcpc()==null?"0":trtg.getEcpc());
                    trtg.setEcpm(trtg.getEcpm()==null?"0":trtg.getEcpm());
                    trtg.setAdPv(trtg.getAdPv()==null?"0":trtg.getAdPv());
                    trtg.setCtr(trtg.getCtr()==null?"0":trtg.getCtr());

                    Double collectionAndBuy = Double.parseDouble(trtg.getDirShopColNum()) + Double.parseDouble(trtg.getInshopItemColNum()) + Double.parseDouble(trtg.getCartNum());
                    if (collectionAndBuy ==0){
                        trtg.setTotalCollectionPlusCost("0");
                        trtg.setTotalCollectionRate("0");
                    }else {
                        trtg.setTotalCollectionPlusCost(trtg.getCharge()==null?"0":String.valueOf(Double.parseDouble(trtg.getCharge()) / collectionAndBuy));
                        trtg.setTotalCollectionRate(trtg.getClick()==null?"0":String.valueOf(Double.parseDouble(trtg.getClick()) / collectionAndBuy));
                    }
                    if (Double.parseDouble(trtg.getCharge()) ==0){
                        trtg.setCommodityPurchaseRate("0");
                        trtg.setTotalCollectionPlusCost("0");
                    }else {
                        trtg.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(trtg.getCartNum()) / Double.parseDouble(trtg.getCharge())));

                    }
                    if (Double.parseDouble(trtg.getClick())==0){
                        trtg.setCommodityCollectionRate("0");
                        trtg.setTotalCollectionRate("0");
                    }else {
                        trtg.setCommodityCollectionRate(String.valueOf(Double.parseDouble(trtg.getInshopItemColNum()) / Double.parseDouble(trtg.getClick())));

                    }
                    if (Double.parseDouble(trtg.getInshopItemColNum())==0){
                        trtg.setCommodityCollectionCost("0");
                    }else {
                        trtg.setCommodityCollectionCost(String.valueOf(Double.parseDouble(trtg.getCharge()) / Double.parseDouble(trtg.getInshopItemColNum())));
                    }
                    trtg.setCommodityPlusCost(String.valueOf(Double.parseDouble(trtg.getCharge()==null?"0":trtg.getCharge()) + Double.parseDouble(trtg.getCartNum()==null?"0":trtg.getCartNum())));
                    if(Double.parseDouble(trtg.getUv())==0){
                        trtg.setAverageUvValue("0");
                    }else {
                        trtg.setAverageUvValue(String.valueOf(Double.parseDouble(trtg.getAlipayInshopAmt()) / Double.parseDouble(trtg.getUv())));
                    }
                    if (Double.parseDouble(trtg.getAlipayInShopNum())==0) {
                        trtg.setOrderAverageAmount("0");
                        trtg.setAverageCostOfOrder("0");
                    }else {
                        trtg.setOrderAverageAmount(String.valueOf(Double.parseDouble(trtg.getAlipayInshopAmt()) / Double.parseDouble(trtg.getAlipayInShopNum())));
                        trtg.setAverageCostOfOrder(String.valueOf(Double.parseDouble(trtg.getCharge()) / Double.parseDouble(trtg.getAlipayInShopNum())));
                    }
                    targetRptsTotalGetMapper.insert(trtg);

                }
            }
        }
        return "";
    }
}
