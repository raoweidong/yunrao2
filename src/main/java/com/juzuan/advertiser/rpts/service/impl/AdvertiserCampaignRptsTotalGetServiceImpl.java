package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserCampaignRptsTotalGetMapper;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.*;
import com.juzuan.advertiser.rpts.service.AdvertiserCampaignRptsTotalGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserCampaignRptsTotalGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserCampaignRptsTotalGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdvertiserCampaignRptsTotalGetServiceImpl implements AdvertiserCampaignRptsTotalGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private AdvertiserCampaignRptsTotalGetMapper advertiserCampaignRptsTotalGetMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    @Override
    public String parseCampaign(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
        //List<CampaignList> campaignLists=campaignListMapper.selectAllCampaign();
        for (TaobaoAuthorizeUser taobaoAuthorizeUser:taobaoAuthorizeUsers){
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiAdvertiserCampaignRptsTotalGetRequest req = new ZuanshiAdvertiserCampaignRptsTotalGetRequest();
            req.setStartTime("2018-08-29");
            req.setEndTime("2018-11-27");
            req.setEffect(7L);
            req.setCampaignModel(1L);
            req.setEffectType("click");
            req.setPageSize(200L);
            req.setOffset(0L);
            ZuanshiAdvertiserCampaignRptsTotalGetResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());
            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_advertiser_campaign_rpts_total_get_response");
           // System.out.println(taobaoAuthorizeUser.getTaobaoUserId()+"  "+campaign.getCampaignId() +"  "+onee.toString());
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject tw=two.getJSONObject("campaign_offline_rpt_total_list");
            if (tw.size()!=0){
                JSONArray three=tw.getJSONArray("data");

                for (Object ob:three.toArray()){
                    System.out.println("遍历目标数组"+ob.toString());
                }
                List<CampaignRptsDayGetData> campaignRptsDayGets=JSONObject.parseArray(three.toString(), CampaignRptsDayGetData.class);
                //遍历json数据属性对象
                for (CampaignRptsDayGetData campaignRptsDayGet:campaignRptsDayGets){
                    AdvertiserCampaignRptsTotalGet advertiserCampaignRptsTotalGet=new AdvertiserCampaignRptsTotalGet();//创建表中对象
                    System.out.println("遍历对象数组  "+campaignRptsDayGet.toString());
                    //插入计算的属性值
                    if (campaignRptsDayGet.getAdPv()==null){
                        campaignRptsDayGet.setAdPv("0");
                    }
                    //campaignRptsDayGetData.setCartNum(campaignRptsDayGetData.getCartNum()==null?"0":campaignRptsDayGetData.getCartNum());
                    if (campaignRptsDayGet.getAlipayInshopAmt()==null){
                        campaignRptsDayGet.setAlipayInshopAmt("0");
                    }
                    if (campaignRptsDayGet.getAlipayInShopNum()==null){
                        campaignRptsDayGet.setAlipayInShopNum("0");
                    }
                    if (campaignRptsDayGet.getAvgAccessPageNum()==null){
                        campaignRptsDayGet.setAvgAccessPageNum("0");
                    }

                    if (campaignRptsDayGet.getCampaignId()==null){
                        campaignRptsDayGet.setCampaignId("0");
                    }
                    if (campaignRptsDayGet.getCampaignName()==null){
                        campaignRptsDayGet.setCampaignName("0");
                    }
                    if (campaignRptsDayGet.getCartNum()==null){
                        campaignRptsDayGet.setCartNum("0");
                    }
                    if (campaignRptsDayGet.getAvgAccessTime()==null){
                        campaignRptsDayGet.setAvgAccessTime("0");
                    }
                    if (campaignRptsDayGet.getCharge()==null){
                        campaignRptsDayGet.setCharge("0");
                    }
                    if (campaignRptsDayGet.getClick()==null){
                        campaignRptsDayGet.setClick("0");
                    }
                    if (campaignRptsDayGet.getCtr()==null){
                        campaignRptsDayGet.setCtr("0");
                    }
                    if (campaignRptsDayGet.getCvr()==null){
                        campaignRptsDayGet.setCvr("0");
                    }
                    if (campaignRptsDayGet.getDeepInshopUv()==null){
                        campaignRptsDayGet.setDeepInshopUv("0");
                    }
                    if (campaignRptsDayGet.getUv()==null){
                        campaignRptsDayGet.setUv("0");
                    }
                    if (campaignRptsDayGet.getRoi()==null){
                        campaignRptsDayGet.setRoi("0");
                    }
                    if (campaignRptsDayGet.getDirShopColNum()==null){
                        campaignRptsDayGet.setDirShopColNum("0");
                    }
                    if (campaignRptsDayGet.getEcpc()==null){
                        campaignRptsDayGet.setEcpc("0");
                    }
                    if (campaignRptsDayGet.getEcpm()==null){
                        campaignRptsDayGet.setEcpm("0");
                    }
                    if (campaignRptsDayGet.getGmvInshopAmt()==null){
                        campaignRptsDayGet.setGmvInshopAmt("0");
                    }
                    if (campaignRptsDayGet.getGmvInshopNum()== null){
                        campaignRptsDayGet.setGmvInshopNum("0");
                    }
                    if (campaignRptsDayGet.getCartNum()==null){
                        campaignRptsDayGet.setCartNum("0");
                    }
                    if (campaignRptsDayGet.getLogDate()==null){
                        campaignRptsDayGet.setLogDate("0");
                    }
                    if (campaignRptsDayGet.getInshopItemColNum()==null){
                        campaignRptsDayGet.setInshopItemColNum("0");
                    }
                    BeanUtils.copyProperties(campaignRptsDayGet,advertiserCampaignRptsTotalGet);//反射属性值
                    advertiserCampaignRptsTotalGet.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                    if (Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())==0){
                        advertiserCampaignRptsTotalGet.setCommodityPurchaseRate("0");//点击量为零
                        advertiserCampaignRptsTotalGet.setCommodityCollectionRate("0");
                        advertiserCampaignRptsTotalGet.setTotalCollectionRate("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum())/Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())));//加购率=添加购物车量/点击量
                        advertiserCampaignRptsTotalGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum())/Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())));//收藏率=收藏宝贝量/点击量
                        advertiserCampaignRptsTotalGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())/Double.parseDouble(advertiserCampaignRptsTotalGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                    }
                    Double collectionAndBuy=Double.parseDouble(advertiserCampaignRptsTotalGet.getDirShopColNum())+Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum())+Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum());
                    if (collectionAndBuy==0){
                        advertiserCampaignRptsTotalGet.setTotalCollectionPlusCost("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                    }
                    if (Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum())==0){
                        advertiserCampaignRptsTotalGet.setCommodityCollectionCost("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge())/Double.parseDouble(advertiserCampaignRptsTotalGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                    }
                    if (Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum())==0){
                        advertiserCampaignRptsTotalGet.setCommodityPlusCost("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge())/Double.parseDouble(advertiserCampaignRptsTotalGet.getCartNum())));//加购成本=消耗/添加购物车量
                    }
                    if (Double.parseDouble(advertiserCampaignRptsTotalGet.getUv())==0){
                        advertiserCampaignRptsTotalGet.setAverageUvValue("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInshopAmt())/Double.parseDouble(advertiserCampaignRptsTotalGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                    }
                    if (Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInshopAmt())==0){
                        advertiserCampaignRptsTotalGet.setOrderAverageAmount("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInshopAmt())/Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                    }
                    if (Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge())==0){
                        advertiserCampaignRptsTotalGet.setAverageCostOfOrder("0");
                    }
                    else {
                        advertiserCampaignRptsTotalGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserCampaignRptsTotalGet.getCharge())/Double.parseDouble(advertiserCampaignRptsTotalGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                    }

                    //advertiserCampaignRptsTotalGetMapper.insert(advertiserCampaignRptsTotalGet);

                    System.out.println("呵呵呵");
                }
            } else {

                System.out.println("没有获取的信息");
                continue;

            }


        }
        return "";
    }

    public void saveCampaign(String json){
        JSONObject one= JSON.parseObject(json);
        JSONObject  getResponse=one.getJSONObject("zuanshi_advertiser_campaign_rpts_total_get_response");
        JSONObject rptDaysList=getResponse.getJSONObject("campaign_offline_rpt_days_list");
        System.out.println("campaign_offline_rpt_days_list  "+rptDaysList.toString());
        //省略许多







    }


}
