package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.*;
import com.juzuan.advertiser.rpts.model.*;
import com.juzuan.advertiser.rpts.service.AdvertiserAdzoneRptsDayGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAdzoneRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAdzoneRptsDayGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钻展资源位分日列表数据
 */
@Service
public class AdvertiserAdzoneRptsDayGetServiceImpl implements AdvertiserAdzoneRptsDayGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdzoneListMapper adzoneListMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private AdvertiserAdzoneRptsDayGetMapper advertiserAdzoneRptsDayGetMapper;
    @Autowired
    private AdzoneListBindMapper adzoneListBindMapper;
    @Autowired
    private RequestMapper requestMapper;
    //@Scheduled(cron = "0 0 3 * * ?")
    public String getAdzone(){
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
            List<AdzoneListBind> adzoneListBindd = adzoneListBindMapper.selectAllAdzoneListBind();
            for (AdzoneListBind ad : adzoneListBindd) {
                Long adg = ad.getCampaignId();
                Long cam = ad.getAdgroupId();
                Long adz = ad.getAdzoneId();
                String userId = ad.getTaobaoUserId();
                TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(userId);
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiAdvertiserAdzoneRptsDayGetRequest req = new ZuanshiAdvertiserAdzoneRptsDayGetRequest();
                req.setStartTime(yestime);
                req.setEndTime(time);
                req.setCampaignId(adg);
                req.setAdgroupId(cam);
                req.setAdzoneId(adz);
                req.setEffect(7L);
                req.setCampaignModel(1L);
                req.setEffectType("click");
                ZuanshiAdvertiserAdzoneRptsDayGetResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("钻展资源位分日列表数据 " + rsp.getBody());
                JSONObject one = JSON.parseObject(rsp.getBody());
                JSONObject onee = one.getJSONObject("zuanshi_advertiser_adzone_rpts_day_get_response");
                JSONObject two = JSON.parseObject(onee.toString());
                JSONObject twoo = two.getJSONObject("adzone_offline_rpt_days_list");//包含data的json对象
                if (twoo.size() == 0) {
                    System.out.println("为空");
                } else {
                    JSONObject thre = JSON.parseObject(twoo.toString());
                    JSONArray three = thre.getJSONArray("data");
                    List<AdvertiserAdzoneRptsDayGet> adzoneRptsDayss = JSONObject.parseArray(three.toString(), AdvertiserAdzoneRptsDayGet.class);
                    for (AdvertiserAdzoneRptsDayGet adzoneRptsDay : adzoneRptsDayss) {
                        adzoneRptsDay.setAdgroupName(adzoneRptsDay.getAdzoneName() == null ? "0" : adzoneRptsDay.getAdzoneName());
                        adzoneRptsDay.setCampaignId(adzoneRptsDay.getCampaignId() == null ? 0L : adzoneRptsDay.getCampaignId());
                        adzoneRptsDay.setAdgroupId(adzoneRptsDay.getAdgroupId() == null ? 0L : adzoneRptsDay.getAdgroupId());
                        adzoneRptsDay.setAdzoneId(adzoneRptsDay.getAdzoneId() == null ? 0L : adzoneRptsDay.getAdzoneId());
                        adzoneRptsDay.setAdgroupName(adzoneRptsDay.getAdzoneName() == null ? "0" : adzoneRptsDay.getAdzoneName());
                        adzoneRptsDay.setCampaignName(adzoneRptsDay.getCampaignName() == null ? "0" : adzoneRptsDay.getCampaignName());
                        adzoneRptsDay.setCvr(adzoneRptsDay.getCvr() == null ? "0" : adzoneRptsDay.getCvr());
                        adzoneRptsDay.setAlipayInShopNum(adzoneRptsDay.getAlipayInShopNum() == null ? "0" : adzoneRptsDay.getAlipayInShopNum());
                        adzoneRptsDay.setAlipayInshopAmt(adzoneRptsDay.getAlipayInshopAmt() == null ? "0" : adzoneRptsDay.getAlipayInshopAmt());
                        adzoneRptsDay.setGmvInshopAmt(adzoneRptsDay.getGmvInshopAmt() == null ? "0" : adzoneRptsDay.getGmvInshopAmt());
                        adzoneRptsDay.setGmvInshopNum(adzoneRptsDay.getGmvInshopNum() == null ? "0" : adzoneRptsDay.getGmvInshopNum());
                        adzoneRptsDay.setCartNum(adzoneRptsDay.getCartNum() == null ? "0" : adzoneRptsDay.getCartNum());
                        adzoneRptsDay.setDirShopColNum(adzoneRptsDay.getDirShopColNum() == null ? "0" : adzoneRptsDay.getDirShopColNum());
                        adzoneRptsDay.setInshopItemColNum(adzoneRptsDay.getInshopItemColNum() == null ? "0" : adzoneRptsDay.getInshopItemColNum());
                        adzoneRptsDay.setAvgAccessPageNum(adzoneRptsDay.getAvgAccessPageNum() == null ? "0" : adzoneRptsDay.getAvgAccessPageNum());
                        adzoneRptsDay.setAvgAccessTime(adzoneRptsDay.getAvgAccessTime() == null ? "0" : adzoneRptsDay.getAvgAccessTime());
                        adzoneRptsDay.setDeepInshopUv(adzoneRptsDay.getDeepInshopUv() == null ? "0" : adzoneRptsDay.getDeepInshopUv());
                        adzoneRptsDay.setUv(adzoneRptsDay.getUv() == null ? "0" : adzoneRptsDay.getUv());
                        adzoneRptsDay.setEcpm(adzoneRptsDay.getEcpm() == null ? "0" : adzoneRptsDay.getEcpm());
                        adzoneRptsDay.setEcpc(adzoneRptsDay.getEcpc() == null ? "0" : adzoneRptsDay.getEcpc());
                        adzoneRptsDay.setCtr(adzoneRptsDay.getCtr() == null ? "0" : adzoneRptsDay.getCtr());
                        adzoneRptsDay.setCharge(adzoneRptsDay.getCharge() == null ? "0" : adzoneRptsDay.getCharge());
                        adzoneRptsDay.setClick(adzoneRptsDay.getClick() == null ? "0" : adzoneRptsDay.getClick());
                        adzoneRptsDay.setAdPv(adzoneRptsDay.getAdPv() == null ? "0" : adzoneRptsDay.getAdPv());
                        adzoneRptsDay.setRoi(adzoneRptsDay.getRoi() == null ? "0" : adzoneRptsDay.getRoi());
                        AdvertiserAdzoneRptsDayGet advertiserAdzoneRptsDayGet = new AdvertiserAdzoneRptsDayGet();
                        BeanUtils.copyProperties(adzoneRptsDay, advertiserAdzoneRptsDayGet);
                        Double collectionAndBuy = Double.parseDouble(advertiserAdzoneRptsDayGet.getDirShopColNum()) + Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum()) + Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum());
                        if (collectionAndBuy == 0) {
                            advertiserAdzoneRptsDayGet.setTotalCollectionPlusCost("0");
                            advertiserAdzoneRptsDayGet.setTotalCollectionRate("0");
                        } else {
                            advertiserAdzoneRptsDayGet.setTotalCollectionPlusCost(advertiserAdzoneRptsDayGet.getCharge() == null ? "0" : String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge()) / collectionAndBuy));
                            advertiserAdzoneRptsDayGet.setTotalCollectionRate(advertiserAdzoneRptsDayGet.getClick() == null ? "0" : String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getClick()) / collectionAndBuy));
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge()) == 0) {
                            advertiserAdzoneRptsDayGet.setCommodityPurchaseRate("0");
                            advertiserAdzoneRptsDayGet.setTotalCollectionPlusCost("0");
                        } else {
                            advertiserAdzoneRptsDayGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum()) / Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge())));
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsDayGet.getClick()) == 0) {
                            advertiserAdzoneRptsDayGet.setCommodityCollectionRate("0");
                            advertiserAdzoneRptsDayGet.setTotalCollectionRate("0");
                        } else {
                            advertiserAdzoneRptsDayGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum()) / Double.parseDouble(advertiserAdzoneRptsDayGet.getClick())));
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum()) == 0) {
                            advertiserAdzoneRptsDayGet.setCommodityCollectionCost("0");
                        } else {
                            advertiserAdzoneRptsDayGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge()) / Double.parseDouble(advertiserAdzoneRptsDayGet.getInshopItemColNum())));
                        }
                        advertiserAdzoneRptsDayGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge() == null ? "0" : advertiserAdzoneRptsDayGet.getCharge()) + Double.parseDouble(advertiserAdzoneRptsDayGet.getCartNum() == null ? "0" : advertiserAdzoneRptsDayGet.getCartNum())));
                        if (Double.parseDouble(advertiserAdzoneRptsDayGet.getUv()) == 0) {
                            advertiserAdzoneRptsDayGet.setAverageUvValue("0");
                        } else {
                            advertiserAdzoneRptsDayGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInshopAmt()) / Double.parseDouble(advertiserAdzoneRptsDayGet.getUv())));
                        }
                        if (Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInShopNum()) == 0) {
                            advertiserAdzoneRptsDayGet.setOrderAverageAmount("0");
                            advertiserAdzoneRptsDayGet.setAverageCostOfOrder("0");
                        } else {
                            advertiserAdzoneRptsDayGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInshopAmt()) / Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInShopNum())));
                            advertiserAdzoneRptsDayGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserAdzoneRptsDayGet.getCharge()) / Double.parseDouble(advertiserAdzoneRptsDayGet.getAlipayInShopNum())));
                        }
                        advertiserAdzoneRptsDayGet.setTaobaoUserId(ad.getTaobaoUserId());
                        Map<String, Object> data = new HashMap<>();
                        data.put("currIndex", 0);
                        data.put("pageSize", 10);
                        List<AdvertiserAdzoneRptsDayGet> advertiserAdzoneRptsDayGets = advertiserAdzoneRptsDayGetMapper.queryAdzoneRptsDayGetsBySql(data);
                        advertiserAdzoneRptsDayGetMapper.insert(advertiserAdzoneRptsDayGet);

                    }


                }

            }
        }
        return "";
    }

    public static void main(String[] args) {

    }

}

