package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdzoneListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdzoneList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AdzoneListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerAdzoneFindpageRequest;
import com.taobao.api.response.ZuanshiBannerAdzoneFindpageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钻石展位全店广告位分页数据
 */
@Service
public class AdzoneListServiceImpl implements AdzoneListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdzoneListMapper adzoneListMapper;
    //定时更新：每天2:00
    @Scheduled(cron = "0 0 2 * * ? ")
    public String getAdzoneList(){
        adzoneListMapper.deleteALL();
        Long totalCount = null;
        long[] count =new long[2];
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser taobao : taobaoAuthorizeUsers) {
            String sessionKey = taobao.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerAdzoneFindpageRequest req = new ZuanshiBannerAdzoneFindpageRequest();
            req.setPageSize(50L);
            req.setPageNum(1L);
            ZuanshiBannerAdzoneFindpageResponse rsp = null;
            try {
                rsp = client.execute(req, sessionKey);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            //System.out.println("资源位的列表的json  " + rsp.getBody());
            JSONObject one = JSON.parseObject(rsp.getBody());
            JSONObject onee = one.getJSONObject("zuanshi_banner_adzone_findpage_response");
            JSONObject two = JSON.parseObject(onee.toString());
            JSONObject twoo = two.getJSONObject("result");
            JSONObject thre = JSON.parseObject(twoo.toString());

            totalCount = Long.valueOf(thre.getString("total_count"));
            for (int i=0;i<count.length-1;i++){
                count[i]=totalCount;
            }
            continue;
        }
        long maxIndex =count[0];
        for (int i = 0; i < count.length; i++) {
            if (maxIndex < count[i]) {
                maxIndex = count[i];
            }
        }
        //System.out.println(maxIndex);
        Long length = totalCount/50+1;
        for (TaobaoAuthorizeUser taobaoAuthorizeUser : taobaoAuthorizeUsers) {
            for (Long i =1L;i<=length;i++) {
                String sessionKey = taobaoAuthorizeUser.getAccessToken();
                TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
                ZuanshiBannerAdzoneFindpageRequest req = new ZuanshiBannerAdzoneFindpageRequest();
                req.setPageSize(50L);
                req.setPageNum(i);
                ZuanshiBannerAdzoneFindpageResponse rsp = null;
                try {
                    rsp = client.execute(req, sessionKey);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                System.out.println("资源位的列表的json  " + rsp.getBody());
                JSONObject one = JSON.parseObject(rsp.getBody());
                JSONObject onee = one.getJSONObject("zuanshi_banner_adzone_findpage_response");
                JSONObject two = JSON.parseObject(onee.toString());
                JSONObject twoo = two.getJSONObject("result");
                JSONObject thre = JSON.parseObject(twoo.toString());
                JSONObject three = thre.getJSONObject("adzones");
                JSONArray four = three.getJSONArray("adzone_d_t_o");
                AdzoneList adzoneList = new AdzoneList();
                if (four == null) {
                    continue;
                } else {
                    for (Object ob : four.toArray()) {
                        //System.out.println("  资源位数组  " + ob.toString());
                        JSONObject oo = JSON.parseObject(ob.toString());
                            adzoneList.setAdzoneId(oo.getLong("adzone_id"));
                            //System.out.println("  资源位id  " + adzoneList.getAdzoneId());
                            adzoneList.setAdzoneName(oo.getString("adzone_name"));
                            //adzoneList.setAdzoneSizeList(oo.getString("adzone_size_list"));
                            //资源位尺寸的获取
                            JSONObject sizeList = oo.getJSONObject("adzone_size_list");
                            JSONArray string = sizeList.getJSONArray("string");
                            //System.out.println("  尺寸数据  " + string.toString());
                            String str = string.toString().substring(1, string.toString().length() - 1);
                            //System.out.println("  去掉中括号 " + str);
                            String strr = str.replaceAll("\"", "");
                            //System.out.println("haahh " + strr);
                            adzoneList.setAdzoneSizeList(strr);
                            JSONObject format = oo.getJSONObject("allow_ad_format_list");
                            String formatList = format.getString("number");
                            String num = formatList.substring(1, formatList.length() - 1);
                            adzoneList.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
                            adzoneList.setAllowAdFormatList(num);
                            adzoneList.setAllowAdvType(oo.getLong("allow_adv_type"));
                            adzoneList.setAdzoneLevel(oo.getLong("adzone_level"));
                            adzoneList.setMediaType(oo.getLong("media_type"));
                            adzoneList.setTpv(oo.getLong("tpv"));
                            adzoneList.setCtr(oo.getString("ctr"));
                            adzoneList.setFirstTime(oo.getDate("first_time"));
                            adzoneList.setRcmdScore(oo.getLong("rcmd_score"));
                            adzoneList.setBidScore(oo.getLong("bid_score"));
                            adzoneList.setCpmScore(oo.getLong("cpm_score"));
                            adzoneList.setCpcScore(oo.getLong("cpc_score"));
                            adzoneListMapper.insert(adzoneList);

                    }
                }
            }
        }
        return "";
    }
}
