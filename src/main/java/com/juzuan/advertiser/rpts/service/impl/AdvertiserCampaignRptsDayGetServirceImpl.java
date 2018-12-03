package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserCampaignRptsDayGetMapper;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserCampaignRptsDayGet;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.CampaignRptsDayGetData;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdvertiserCampaignRptsDayGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserCampaignRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserCampaignRptsDayGetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class AdvertiserCampaignRptsDayGetServirceImpl implements AdvertiserCampaignRptsDayGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private CampaignListMapper campaignListMapper;
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdvertiserCampaignRptsDayGetMapper advertiserCampaignRptsDayGetMapper;
    //@Scheduled( cron = "*/5 * * * * ?")
    public String getAdvertiserCampaignRptsDay(){
        List<CampaignList> campaignLists=campaignListMapper.selectAllCampaign();
        for (CampaignList campaignList:campaignLists){
            Long id=campaignList.getCampaignId();
            TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.slectByUserId(campaignList.getTaobaoUserId());
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiAdvertiserCampaignRptsDayGetRequest req = new ZuanshiAdvertiserCampaignRptsDayGetRequest();
            req.setStartTime("2018-08-29");
            req.setEndTime("2018-11-27");
            req.setCampaignId(id);
            req.setEffect(7L);
            req.setCampaignModel(1L);
            req.setEffectType("click");
            ZuanshiAdvertiserCampaignRptsDayGetResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());

            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_advertiser_campaign_rpts_day_get_response");
            System.out.println(taobaoAuthorizeUser.getTaobaoUserId()+"  "+campaignList.getCampaignId() +"  "+onee.toString());
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject twoo=two.getJSONObject("campaign_offline_rpt_days_list");
            if (twoo.size()!=0){
                JSONArray three=twoo.getJSONArray("data");
                System.out.println("目标数组  "+three.toString());
                for (Object ob:three.toArray()){
                    System.out.println("遍历目标数组"+ob.toString());
                }
                List<CampaignRptsDayGetData> campaignRptsDayGetDatas=JSONObject.parseArray(three.toString(), CampaignRptsDayGetData.class);
                //遍历json数据属性对象
                for (CampaignRptsDayGetData campaignRptsDayGetData:campaignRptsDayGetDatas){
                    AdvertiserCampaignRptsDayGet advertiserCampaignRptsDayGet=new AdvertiserCampaignRptsDayGet();//创建表中对象
                    System.out.println("遍历对象数组  "+campaignRptsDayGetData.toString());
                    //插入计算的属性值
                    if (campaignRptsDayGetData.getAdPv()==null){
                        campaignRptsDayGetData.setAdPv("0");
                    }
                    //campaignRptsDayGetData.setCartNum(campaignRptsDayGetData.getCartNum()==null?"0":campaignRptsDayGetData.getCartNum());
                    if (campaignRptsDayGetData.getAlipayInshopAmt()==null){
                        campaignRptsDayGetData.setAlipayInshopAmt("0");
                    }
                    if (campaignRptsDayGetData.getAlipayInShopNum()==null){
                        campaignRptsDayGetData.setAlipayInShopNum("0");
                    }
                    if (campaignRptsDayGetData.getAvgAccessPageNum()==null){
                        campaignRptsDayGetData.setAvgAccessPageNum("0");
                    }

                    if (campaignRptsDayGetData.getCampaignId()==null){
                        campaignRptsDayGetData.setCampaignId(String.valueOf(campaignList.getCampaignId()));
                    }
                    if (campaignRptsDayGetData.getCampaignName()==null){
                        campaignRptsDayGetData.setCampaignName(campaignList.getCampaignName());
                    }
                    if (campaignRptsDayGetData.getCartNum()==null){
                        campaignRptsDayGetData.setCartNum("0");
                    }
                    if (campaignRptsDayGetData.getAvgAccessTime()==null){
                        campaignRptsDayGetData.setAvgAccessTime("0");
                    }
                    if (campaignRptsDayGetData.getCharge()==null){
                        campaignRptsDayGetData.setCharge("0");
                    }
                    if (campaignRptsDayGetData.getClick()==null){
                        campaignRptsDayGetData.setClick("0");
                    }
                    if (campaignRptsDayGetData.getCtr()==null){
                        campaignRptsDayGetData.setCtr("0");
                    }
                    if (campaignRptsDayGetData.getCvr()==null){
                        campaignRptsDayGetData.setCvr("0");
                    }
                    if (campaignRptsDayGetData.getDeepInshopUv()==null){
                        campaignRptsDayGetData.setDeepInshopUv("0");
                    }
                    if (campaignRptsDayGetData.getUv()==null){
                        campaignRptsDayGetData.setUv("0");
                    }
                    if (campaignRptsDayGetData.getRoi()==null){
                        campaignRptsDayGetData.setRoi("0");
                    }
                    if (campaignRptsDayGetData.getDirShopColNum()==null){
                        campaignRptsDayGetData.setDirShopColNum("0");
                    }
                    if (campaignRptsDayGetData.getEcpc()==null){
                        campaignRptsDayGetData.setEcpc("0");
                    }
                    if (campaignRptsDayGetData.getEcpm()==null){
                        campaignRptsDayGetData.setEcpm("0");
                    }
                    if (campaignRptsDayGetData.getGmvInshopAmt()==null){
                        campaignRptsDayGetData.setGmvInshopAmt("0");
                    }
                    if (campaignRptsDayGetData.getGmvInshopNum()== null){
                        campaignRptsDayGetData.setGmvInshopNum("0");
                    }
                    if (campaignRptsDayGetData.getCartNum()==null){
                        campaignRptsDayGetData.setCartNum("0");
                    }
                    if (campaignRptsDayGetData.getLogDate()==null){
                        campaignRptsDayGetData.setLogDate("0");
                    }
                    if (campaignRptsDayGetData.getInshopItemColNum()==null){
                        campaignRptsDayGetData.setInshopItemColNum("0");
                    }                    BeanUtils.copyProperties(campaignRptsDayGetData,advertiserCampaignRptsDayGet);//反射属性值
                    advertiserCampaignRptsDayGet.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                    if (Double.parseDouble(advertiserCampaignRptsDayGet.getClick())==0){
                        advertiserCampaignRptsDayGet.setCommodityPurchaseRate("0");//点击量为零
                        advertiserCampaignRptsDayGet.setCommodityCollectionRate("0");
                        advertiserCampaignRptsDayGet.setTotalCollectionRate("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getCartNum())/Double.parseDouble(advertiserCampaignRptsDayGet.getClick())));//加购率=添加购物车量/点击量
                        advertiserCampaignRptsDayGet.setCommodityCollectionRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getInshopItemColNum())/Double.parseDouble(advertiserCampaignRptsDayGet.getClick())));//收藏率=收藏宝贝量/点击量
                        advertiserCampaignRptsDayGet.setTotalCollectionRate(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getClick())/Double.parseDouble(advertiserCampaignRptsDayGet.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量

                    }
                    Double collectionAndBuy=Double.parseDouble(advertiserCampaignRptsDayGet.getDirShopColNum())+Double.parseDouble(advertiserCampaignRptsDayGet.getInshopItemColNum())+Double.parseDouble(advertiserCampaignRptsDayGet.getCartNum());
                    if (collectionAndBuy==0){
                        advertiserCampaignRptsDayGet.setTotalCollectionPlusCost("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                    }
                    if (Double.parseDouble(advertiserCampaignRptsDayGet.getInshopItemColNum())==0){
                        advertiserCampaignRptsDayGet.setCommodityCollectionCost("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setCommodityCollectionCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getCharge())/Double.parseDouble(advertiserCampaignRptsDayGet.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
                    }
                    if (Double.parseDouble(advertiserCampaignRptsDayGet.getCartNum())==0){
                        advertiserCampaignRptsDayGet.setCommodityPlusCost("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setCommodityPlusCost(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getCharge())/Double.parseDouble(advertiserCampaignRptsDayGet.getCartNum())));//加购成本=消耗/添加购物车量
                    }
                    if (Double.parseDouble(advertiserCampaignRptsDayGet.getUv())==0){
                        advertiserCampaignRptsDayGet.setAverageUvValue("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setAverageUvValue(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getAlipayInshopAmt())/Double.parseDouble(advertiserCampaignRptsDayGet.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客

                    }
                    if (Double.parseDouble(advertiserCampaignRptsDayGet.getAlipayInshopAmt())==0){
                        advertiserCampaignRptsDayGet.setOrderAverageAmount("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setOrderAverageAmount(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getAlipayInshopAmt())/Double.parseDouble(advertiserCampaignRptsDayGet.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                    }
                    if (Double.parseDouble(advertiserCampaignRptsDayGet.getCharge())==0){
                        advertiserCampaignRptsDayGet.setAverageCostOfOrder("0");
                    }
                    else {
                        advertiserCampaignRptsDayGet.setAverageCostOfOrder(String.valueOf(Double.parseDouble(advertiserCampaignRptsDayGet.getCharge())/Double.parseDouble(advertiserCampaignRptsDayGet.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

                    }

                //advertiserCampaignRptsDayGetMapper.insert(advertiserCampaignRptsDayGet);

                    System.out.println("呵呵呵");
                }
            } else {

                System.out.println("没有获取的信息");
                continue;

            }


        }

        return "";
    }
}
