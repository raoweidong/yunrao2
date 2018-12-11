package com.juzuan.advertiser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoZsRelationshopPackgeTargetConditionMapper;
import com.juzuan.advertiser.rpts.mapper.ZiDianMapper;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerRelationshopPackageConditionFindRequest;
import com.taobao.api.response.ZuanshiBannerRelationshopPackageConditionFindResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BannerRelationshopPackageConditionFind {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private ZiDianMapper ziDianMapper;
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private TaobaoZsRelationshopPackgeTargetConditionMapper taobaoZsRelationshopPackgeTargetConditionMapper;
    @Test
    public void  xiaLaiXuanKuang(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser user:taobaoAuthorizeUsers){
            String sessionKey=user.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerRelationshopPackageConditionFindRequest req = new ZuanshiBannerRelationshopPackageConditionFindRequest();
            ZuanshiBannerRelationshopPackageConditionFindResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());
            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_banner_relationshop_package_condition_find_response");
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject twoo=two.getJSONObject("result");
            JSONObject thre=JSON.parseObject(twoo.toString());
            JSONObject three=thre.getJSONObject("shop_package_query_condition");
            System.out.println("three  "+three.toString());
            JSONObject fou=JSON.parseObject(three.toString());
            System.out.println("fou  "+fou.toString());
        }


    }

}
