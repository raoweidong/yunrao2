package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.CrowdListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.CrowdList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CrowdListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCrowdFindRequest;
import com.taobao.api.response.ZuanshiBannerCrowdFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrowdListServiceImpl implements CrowdListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CrowdListMapper crowdListMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    public String CrowdList() throws ApiException {
        List<AdgroupList> cam = adgroupListMapper.selectAllAdgroup();
        for (AdgroupList adl: cam) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(adl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerCrowdFindRequest req = new ZuanshiBannerCrowdFindRequest();
            req.setCampaignId(adl.getCampaignId());
            req.setAdgroupId(adl.getAdgroupId());
            ZuanshiBannerCrowdFindResponse rsp = client.execute(req,sessionKey);
            System.out.println("全店计划定向人群 : "+rsp.getBody());
            //"zuanshi_banner_crowd_find_response"
            JSONObject zsObject = JSON.parseObject(rsp.getBody());
            JSONObject onObject = zsObject.getJSONObject("zuanshi_banner_crowd_find_response");
            if (onObject==null){
                continue;
            } else {
                //"result"
                JSONObject reObject = JSON.parseObject(onObject.toString());
                JSONObject twObject = reObject.getJSONObject("result");
                //"crowds"
                JSONObject crObject = JSON.parseObject(twObject.toString());
                JSONObject thOnject = crObject.getJSONObject("crowds");
                //判断是否为空
                if (thOnject==null){
                    continue;
                }else {
                    // "crowd_d_t_o":[]
                    JSONObject dtObject = JSON.parseObject(thOnject.toString());
                    JSONArray dto = dtObject.getJSONArray("crowd_d_t_o");
                    //判断数组是否为空
                    if (dto == null) {
                        continue;//为空则直接返回
                    } else {
                        //List<CrowdList> crowdLists = JSONObject.parseArray(dto.toString(),CrowdList.class);
                        for (Object obb : dto.toArray()) {
                            CrowdList cl = new CrowdList();
                            JSONObject ob = JSONObject.parseObject(obb.toString());
                            cl.setTaobaoUserId(adl.getTaobaoUserId());
                            cl.setCampaignId(ob.getLong("campaign_id"));
                            cl.setAdgroupId(ob.getLong("adgroup_id"));
                            cl.setCrowdName(ob.getString("crowd_name"));
                            cl.setCrowdType(ob.getLong("crowd_type"));
                            cl.setCrowdValue(ob.getString("crowd_value"));
                            cl.setGmtCreate(ob.getDate("gmt_create"));
                            cl.setGmtModified(ob.getDate("gmt_modified"));
                            cl.setTargetId(ob.getLong("id"));
                            cl.setMinPerSale(ob.getString("min_per_sale") == null ? "0" : ob.getString("min_per_sale"));
                            cl.setMaxPerSale(ob.getString("max_per_sale") == null ? "0" : ob.getString("max_per_sale"));
                            cl.setShopPreferenceValue(ob.getString("shop_preference_value") == null ? "0" : ob.getString("shop_preference_value"));
                            cl.setCatId(ob.getLong("cat_id") == null ? 0L : ob.getLong("cat_id"));
                            cl.setCatName(ob.getString("cat_name") == null ? "0" : ob.getString("cat_name"));

                            //"matrix_prices":{
                            JSONObject matrix = ob.getJSONObject("matrix_prices");
                            //"matrix_price_d_t_o":[
                            JSONObject mpd = JSONObject.parseObject(matrix.toString());
                            JSONArray pdto = mpd.getJSONArray("matrix_price_d_t_o");
                            if (pdto == null) {
                                cl.setAdzoneId(0L);
                                cl.setPrice(0L);
                            } else {
                                for (Object mob : pdto.toArray()) {
                                    JSONObject mdto = JSONObject.parseObject(mob.toString());
                                    cl.setAdzoneId(mdto.getLong("adzone_id") == null ? 0L : mdto.getLong("adzone_id"));
                                    cl.setPrice(mdto.getLong("price") == null ? 0L : mdto.getLong("price"));
                                }

                            }
                            //"sub_crowds":{
                            JSONObject sub = ob.getJSONObject("sub_crowds");
                            //"sub_crowd_d_t_o":[
                            JSONObject scd = JSONObject.parseObject(sub.toString());
                            JSONArray cdto = scd.getJSONArray("sub_crowd_d_t_o");
                            if (cdto == null) {
                                cl.setSubCrowdValue("0");
                                cl.setSubCrowdName("0");
                            } else {
                                for (Object sob : cdto.toArray()) {
                                    JSONObject subdto = JSONObject.parseObject(sob.toString());
                                    cl.setSubCrowdName(subdto.getString("sub_crowd_name") == null ? "0" : subdto.getString("sub_crowd_name"));
                                    cl.setSubCrowdValue(subdto.getString("sub_crowd_value") == null ? "0" : subdto.getString("sub_crowd_value"));
                                }
                            }
                            //"cat_id_list"
                            JSONArray catIdList = ob.getJSONArray("cat_id_list");
                            if (catIdList == null) {
                                cl.setCatIdList("0");
                            } else {
                                String catId = catIdList.toString().substring(0, 3);
                                cl.setCatIdList(catId);
                            }
                            //"shop_scale_id_list"
                            JSONArray shopIdList = ob.getJSONArray("shop_scale_id_list");
                            if (shopIdList == null) {
                                cl.setShopScaleIdList("0");
                            } else {
                                String shopId = shopIdList.toString().substring(0, 3);
                                cl.setShopScaleIdList(shopId);
                            }
                            crowdListMapper.insert(cl);
                        }
                    }
                }
            }
        }
        return "";
    }
}
