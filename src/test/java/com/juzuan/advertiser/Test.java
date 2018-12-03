package com.juzuan.advertiser;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAdvertiserCampaignRptsDayGetRequest;
import com.taobao.api.request.ZuanshiBannerAdgroupGetRequest;
import com.taobao.api.response.ZuanshiAdvertiserCampaignRptsDayGetResponse;
import com.taobao.api.response.ZuanshiBannerAdgroupGetResponse;

public class Test {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    public static void main(String[] args) {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        String sessionKey="6201e076694a352ZZb95c8490adbb65d8818a1188897d9b3075039900";
       // String sessionKey="62014158e1807b8bcde05f9ZZ146bb4d7fb9807b3af0bd13162788126";
        ZuanshiAdvertiserCampaignRptsDayGetRequest req = new ZuanshiAdvertiserCampaignRptsDayGetRequest();
        req.setStartTime("2017-08-28");
        req.setEndTime("2017-11-25");
        req.setCampaignId(308176558L);
        req.setEffect(7L);
        req.setCampaignModel(1L);
        req.setEffectType("impression");
        ZuanshiAdvertiserCampaignRptsDayGetResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }


}
