package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdvertiserAccountRptsDayGetMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsDayGet;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.query.DayGetQuery;
import com.juzuan.advertiser.rpts.service.RptsDayGetService;
import com.juzuan.advertiser.rpts.service.TaobaoAuthorizeUserService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserAccountRptsDayGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserAccountRptsDayGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
@Service
public class RptsDayGetServiceImpl implements RptsDayGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private RptsDayGetService rptsDayGetService;
    @Autowired
    private AdvertiserAccountRptsDayGetMapper advertiserAccountRptsDayGetMapper;
    //@Scheduled(cron = "*/5 * * * * ?")//五秒钟刷一次
    @Override
    public String rpts(){
        TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.selectByPrimaryKey(1L);
        String sessionKey=taobaoAuthorizeUser.getAccessToken();
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        ZuanshiAdvertiserAccountRptsDayGetRequest req = new ZuanshiAdvertiserAccountRptsDayGetRequest();
        req.setStartTime("2018-08-22");
        req.setEndTime("2018-11-25");
        req.setEffect(7L);
        req.setEffectType("impression");
        ZuanshiAdvertiserAccountRptsDayGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println("正在打印钻展广告主账户数据分日列表查询   "+rsp.getBody());
        if (rsp==null){
            System.out.println("进入解析失败json的方法");
        } else {
            parse(rsp.getBody(),taobaoAuthorizeUser.getTaobaoUserId());
        }
        return rsp.toString();

    }

    private String parse(String json,String taobaoUserId) {
        JSONObject outSideObject = JSON.parseObject(json);
        System.out.println("外层json  "+outSideObject.toString());

        Object inerObject=outSideObject.getJSONObject("zuanshi_advertiser_account_rpts_day_get_response");
        System.out.println("里层json  "+inerObject.toString());

        JSONObject innerObject=JSON.parseObject(inerObject.toString());
        Object daylistObject=innerObject.getJSONObject("account_offline_rpt_days_list");
        System.out.println( " lili  "+daylistObject.toString());

        JSONObject dataObject=JSON.parseObject(daylistObject.toString());
        JSONArray data=dataObject.getJSONArray("data");

        for(Object obj:data.toArray()){
            System.out.println("遍历data数组 "+obj.toString());
        }
        List<AdvertiserAccountRptsDayGet> advertiserAccountRptsDayGet = JSONObject.parseArray(data.toString(), AdvertiserAccountRptsDayGet.class);
        for (AdvertiserAccountRptsDayGet item:advertiserAccountRptsDayGet){
            item.setTaobaoUserId(taobaoUserId);
            item.setCommodityPurchaseRate(String.valueOf(Double.parseDouble(item.getCartNum())/Double.parseDouble(item.getClick())));//加购率=添加购物车量/点击量
            item.setCommodityCollectionRate(String.valueOf(Double.parseDouble(item.getInshopItemColNum())/Double.parseDouble(item.getClick())));//收藏率=收藏宝贝量/点击量
            Double collectionAndBuy=Double.parseDouble(item.getDirShopColNum())+Double.parseDouble(item.getInshopItemColNum())+Double.parseDouble(item.getCartNum());
            item.setTotalCollectionPlusCost(String.valueOf(Double.parseDouble(item.getCharge())/collectionAndBuy));//总收藏加购成本=消耗/（收藏宝贝量+收藏店铺量+添加购物车量 )
            item.setTotalCollectionRate(String.valueOf(Double.parseDouble(item.getClick())/Double.parseDouble(item.getClick())));//总收藏加购率=（收藏宝贝量+收藏店铺量+添加购物车量）/点击量
            item.setCommodityCollectionCost(String.valueOf(Double.parseDouble(item.getCharge())/Double.parseDouble(item.getInshopItemColNum())));//收藏成本=消耗/收藏宝贝量
            item.setCommodityPlusCost(String.valueOf(Double.parseDouble(item.getCharge())/Double.parseDouble(item.getCartNum())));//加购成本=消耗/添加购物车量
            item.setAverageUvValue(String.valueOf(Double.parseDouble(item.getAlipayInshopAmt())/Double.parseDouble(item.getUv())));//平均访客价值 (average_uv_value) = 成交订单金额/访客
            item.setOrderAverageAmount(String.valueOf(Double.parseDouble(item.getAlipayInshopAmt())/Double.parseDouble(item.getAlipayInShopNum())));//订单平均金额(order_average_amount)订单平均金额 = 成交订单金额/成交订单量
            item.setAverageCostOfOrder(String.valueOf(Double.parseDouble(item.getCharge())/Double.parseDouble(item.getAlipayInShopNum())));//订单平均成本(average_cost_of_order)订单平均成本 = 消耗/成交订单量

            DayGetQuery query=new DayGetQuery();   //先查询再插入
            query.setUserId(item.getTaobaoUserId());
            query.setLogDate(item.getLogDate());
            AdvertiserAccountRptsDayGet selectObj = advertiserAccountRptsDayGetMapper.selectByUserIdAndLogDate(query);
            if(selectObj==null){
                advertiserAccountRptsDayGetMapper.insert(item);//遍历对象数组并保存
            }else{
                System.out.println("该记录已存在,");
              /*  System.out.println(selectAll().toString());
                List<AdvertiserAccountRptsDayGet>A=selectAll();

                for (AdvertiserAccountRptsDayGet AA:A){
                    System.out.println(AA.getInshopItemColNum());
                }*/

            }
        }
        return json;
    }

    @Override
    public List<AdvertiserAccountRptsDayGet> selectAll() {
        return advertiserAccountRptsDayGetMapper.selectAll();
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(AdvertiserAccountRptsDayGet record) {
        return 0;
    }

    @Override
    public int insertSelective(AdvertiserAccountRptsDayGet record) {
        return 0;
    }

    @Override
    public AdvertiserAccountRptsDayGet selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(AdvertiserAccountRptsDayGet record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(AdvertiserAccountRptsDayGet record) {
        return 0;
    }
}
