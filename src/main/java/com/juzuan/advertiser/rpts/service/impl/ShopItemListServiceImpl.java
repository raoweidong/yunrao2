package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.ShopItemListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.ShopItemList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.ShopItemListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiShopItemFindRequest;
import com.taobao.api.response.ZuanshiShopItemFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取店铺宝贝列表
 */
@Service
public class ShopItemListServiceImpl implements ShopItemListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private ShopItemListMapper shopItemListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String ShopItemList(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiShopItemFindRequest req = new ZuanshiShopItemFindRequest();
            req.setPageSize(20L);
            req.setPageNum(3L);
            ZuanshiShopItemFindResponse rsp = null;
            try {
                rsp = client.execute(req,tau.getAccessToken());
            }catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("获取店铺宝贝列表 : "+rsp.getBody());

            //解析json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_shop_item_find_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject items = thrObject.getJSONObject("items");
            JSONObject fouObject = JSON.parseObject(items.toString());
            JSONArray dto = fouObject.getJSONArray("item_d_t_o");
            if (dto==null){
                continue;
            }else {
                List<ShopItemList> shopItemLists = JSONObject.parseArray(dto.toString(),ShopItemList.class);
                for (ShopItemList sl : shopItemLists){
                    sl.setTaobaoUserId(tau.getTaobaoUserId());
                    shopItemListMapper.insert(sl);
                }

            }
        }
        return "";
    }
}
