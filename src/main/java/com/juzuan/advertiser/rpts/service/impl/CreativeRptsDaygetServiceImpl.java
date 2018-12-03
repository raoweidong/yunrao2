package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.juzuan.advertiser.rpts.mapper.CreativeListBindMapper;
import com.juzuan.advertiser.rpts.mapper.CreativeRptsDaygetMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CreativeListBind;
import com.juzuan.advertiser.rpts.model.CreativeRptsDayget;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CreativeRptsDaygetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserCreativeRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserCreativeRptsDayGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreativeRptsDaygetServiceImpl implements CreativeRptsDaygetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CreativeRptsDaygetMapper creativeRptsDaygetMapper;
    @Autowired
    private CreativeListBindMapper creativeListBindMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    public String creativeRptsDayget(){
        List<CreativeListBind> creativeListBinds = creativeListBindMapper.selectAllCreativeBind();
        for (CreativeListBind clb: creativeListBinds) {
            TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.slectByUserId(clb.getTaobaoUserId());
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiAdvertiserCreativeRptsDayGetRequest req = new ZuanshiAdvertiserCreativeRptsDayGetRequest();
            req.setStartTime("2018-8-29");
            req.setEndTime("2018-11-27");
            req.setCampaignId(clb.getCampaignId());
            req.setAdgroupId(clb.getAdgroupId());
            req.setCreativeId(clb.getCreativeId());
            req.setEffect(7L);
            req.setCampaignModel(1L);
            req.setEffectType("impression");
            ZuanshiAdvertiserCreativeRptsDayGetResponse rsp = null;
            try {
                rsp = client.execute(req,sessionKey);
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("正在打印钻展创意数据分日列表查询 "+rsp.getBody());
            //获取响应json
            JSONObject response = JSON.parseObject(rsp.getBody());
            System.out.println("外层json  "+response.toString());
            //获取"zuanshi_advertiser_creative_rpts_day_get_response"
            Object tableName = response.getJSONObject("zuanshi_advertiser_creative_rpts_day_get_response");
            System.out.println("里层json  "+tableName.toString());
            //获得"creative_offline_rpt_days_list"
            JSONObject tableObject = JSON.parseObject(tableName.toString());
            Object listName = tableObject.getJSONObject("creative_offline_rpt_days_list");
            System.out.println("llll     "+listName.toString());
            //获得"data"数组
            JSONObject dataObject = JSON.parseObject(listName.toString());
            JSONArray data = dataObject.getJSONArray("data");
            if (data == null){
                continue;
            } else {
                List<CreativeRptsDayget> creativeRptsDaygets = JSONObject.parseArray(data.toString(), CreativeRptsDayget.class);
                //遍历creativeLists,并插入data中的数据
                for (CreativeRptsDayget item : creativeRptsDaygets) {
                    item.setTaobaoUserId(clb.getTaobaoUserId());
                    item.setUv(item.getUv() == null ? "0" : item.getUv());
                    item.setAvgAccessTime(item.getAvgAccessTime() == null ? "0" : item.getAvgAccessTime());
                    item.setCharge(item.getCharge() == null ? "0" : item.getCharge());
                    item.setAlipayInshopAmt(item.getAlipayInshopAmt() == null ? "0" : item.getAlipayInshopAmt());
                    item.setAvgAccessPageNum(item.getAvgAccessPageNum() == null ? "0" : item.getAvgAccessPageNum());
                    item.setDirShopColNum(item.getDirShopColNum() == null ? "0" : item.getDirShopColNum());
                    item.setGmvInshopNum(item.getGmvInshopNum() == null ? "0" : item.getGmvInshopNum());
                    item.setClick(item.getClick() == null ? "0" : item.getClick());
                    item.setRoi(item.getRoi() == null ? "0" : item.getRoi());
                    item.setGmvInshopAmt(item.getGmvInshopAmt() == null ? "0" : item.getGmvInshopAmt());
                    item.setCartNum(item.getCartNum() == null ? "0" : item.getCartNum());
                    item.setDeepInshopUv(item.getDeepInshopUv() == null ? "0" : item.getDeepInshopUv());
                    item.setInshopItemColNum(item.getInshopItemColNum() == null ? "0" : item.getInshopItemColNum());
                    item.setCvr(item.getCvr() == null ? "0" : item.getCvr());
                    item.setAlipayInShopNum(item.getAlipayInShopNum() == null ? "0" : item.getAlipayInShopNum());
                    item.setEcpc(item.getEcpc() == null ? "0" : item.getEcpc());
                    item.setEcpm(item.getEcpm() == null ? "0" : item.getEcpm());
                    item.setAdPv(item.getAdPv() == null ? "0" : item.getAdPv());
                    item.setCtr(item.getCtr() == null ? "0" : item.getCtr());
                    //加购率=添加购物车量/点击量
                    item.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(item.getCartNum()) / Double.parseDouble(item.getCharge())));
                    //收藏率=收藏宝贝量/点击量
                    item.setCommodityCollectionRate(String.valueOf(Double.parseDouble(item.getInshopItemColNum()) / Double.parseDouble(item.getClick())));
                    //总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
                    Double collectionAndBuy = Double.parseDouble(item.getDirShopColNum()) + Double.parseDouble(item.getInshopItemColNum()) + Double.parseDouble(item.getCartNum());
                    item.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(item.getCharge()) / collectionAndBuy));
                    //总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量
                    item.setTotalCollectionRate(String.valueOf(Double.parseDouble(item.getClick()) / collectionAndBuy));
                    //收藏成本=消耗/收藏宝贝量
                    item.setCommodityCollectionCost(String.valueOf(Double.parseDouble(item.getCharge()) / Double.parseDouble(item.getInshopItemColNum())));
                    //加购成本=消耗/添加购物车量
                    item.setCommodityPlusCost(String.valueOf(Double.parseDouble(item.getCharge()) + Double.parseDouble(item.getCartNum())));
                    //平均访客价值 (average_uv_value) = 成交订单金额/访客
                    item.setAverageUvValue(String.valueOf(Double.parseDouble(item.getAlipayInshopAmt()) / Double.parseDouble(item.getUv())));
                    //订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
                    item.setOrderAverageAmount(String.valueOf(Double.parseDouble(item.getAlipayInshopAmt()) / Double.parseDouble(item.getAlipayInShopNum())));
                    //订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量
                    item.setAverageCostOfOrder(String.valueOf(Double.parseDouble(item.getCharge()) / Double.parseDouble(item.getAlipayInShopNum())));
                    creativeRptsDaygetMapper.insert(item);
                }
            }
        }
        return "";
    }


    @Override
    public void parseAndsaveCreativeList(String json) {

    }
}
