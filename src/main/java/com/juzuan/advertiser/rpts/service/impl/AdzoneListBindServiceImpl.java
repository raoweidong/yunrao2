package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.AdzoneListBindMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.AdzoneListBind;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdzoneListBindService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerAdgroupAdzoneFindpageRequest;
import com.taobao.api.response.ZuanshiBannerAdgroupAdzoneFindpageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdzoneListBindServiceImpl implements AdzoneListBindService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdzoneListBindMapper adzoneListBindMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    @Override
    public String getBannerAdgroupAdzone(){
        List<AdgroupList> adgroupLists=adgroupListMapper.selectAllAdgroup();
        for (AdgroupList adgroupList:adgroupLists){
            Long cam=adgroupList.getCampaignId();
            Long adg=adgroupList.getAdgroupId();
            String useId=adgroupList.getTaobaoUserId();
            TaobaoAuthorizeUser taobaoAuthorizeUser =taobaoAuthorizeUserMapper.slectByUserId(useId);
            String sessionKey=taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerAdgroupAdzoneFindpageRequest req = new ZuanshiBannerAdgroupAdzoneFindpageRequest();
            req.setCampaignId(cam);
            req.setAdgroupId(adg);
            ZuanshiBannerAdgroupAdzoneFindpageResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
               
            }
            System.out.println("正在打印输出  "+rsp.getBody());
            JSONObject one= JSON.parseObject(rsp.getBody());
            JSONObject onee=one.getJSONObject("zuanshi_banner_adgroup_adzone_findpage_response");
            JSONObject two=JSON.parseObject(onee.toString());
            JSONObject twoo=two.getJSONObject("result");
            JSONObject thre=JSON.parseObject(twoo.toString());
            JSONObject three=thre.getJSONObject("adzones");
         if (three==null){
             System.out.println("没有绑定的广告位");
         }
         else{
             JSONArray fou=three.getJSONArray("adzone_bid_d_t_o");
             for (Object ob:fou.toArray()){
                 AdzoneListBind adzoneListBind=new AdzoneListBind();
                 JSONObject adzones=JSON.parseObject(ob.toString());
                 System.out.println("正在输出绑定的广告位"+ob.toString());
                 adzoneListBind.setAdgroupId(adg);
                 adzoneListBind.setCampaignId(cam);
                 adzoneListBind.setTaobaoUserId(useId);
                 adzoneListBind.setAdzoneId(adzones.getLong("adzone_id"));
                 adzoneListBind.setAdzoneName(adzones.getString("adzone_name"));
                 JSONObject adzoneSizeList=adzones.getJSONObject("adzone_size_list");
                 String string=adzoneSizeList.getString("string");
                 System.out.println("尺寸  "+string);
                 String stringg=string.substring(1,string.length()-1).replaceAll("\"","");
                 adzoneListBind.setAdzoneSizeList(stringg);
                 JSONObject allowAdFormatList=adzones.getJSONObject("allow_ad_format_list");
                 String num=allowAdFormatList.getString("number").substring(1,allowAdFormatList.getString("number").length()-1);
                 adzoneListBind.setAllowAdFormatList(num);
                 adzoneListBind.setAllowAdvType(adzones.getLong("allow_adv_type"));
                 adzoneListBind.setMediaType(adzones.getLong("media_type"));
                 adzoneListBind.setAdzoneLevel(adzones.getLong("adzone_level"));
                 JSONObject matrixPriceList=adzones.getJSONObject("matrix_price_list");
                 JSONArray matrixPriceDTO=matrixPriceList.getJSONArray("matrix_price_d_t_o");
                 for (Object bb:matrixPriceDTO.toArray()){
                     JSONObject bbb=JSON.parseObject(bb.toString());
                     adzoneListBind.setCrowdId(bbb.getLong("crowd_id"));
                     adzoneListBind.setCrowdType(bbb.getLong("crowd_type"));
                     adzoneListBind.setPrice(bbb.getLong("price"));
                 }
                 adzoneListBindMapper.insert(adzoneListBind);
             }

         }
        }
        return "";
    }
}
