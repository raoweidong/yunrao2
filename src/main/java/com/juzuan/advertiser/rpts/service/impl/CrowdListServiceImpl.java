package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.*;
import com.juzuan.advertiser.rpts.model.*;
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
    private static String appkey = "25139411";
    private static String url = "https://eco.taobao.com/router/rest";
    private static String secret = "ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CrowdListMapper crowdListMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private MylikeTargetListMapper mylikeTargetListMapper;
    @Autowired
    private IndependentshopTargetListMapper independentshopTargetListMapper;
    @Autowired
    private SimlikeTargetListMapper simlikeTargetListMapper;
    @Autowired
    private MainstoreTargetListMapper mainstoreTargetListMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String CrowdList() throws ApiException {
        List<AdgroupList> cam = adgroupListMapper.selectAllAdgroup();
        for (AdgroupList adl : cam) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(adl.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerCrowdFindRequest req = new ZuanshiBannerCrowdFindRequest();
            req.setCampaignId(adl.getCampaignId());
            req.setAdgroupId(adl.getAdgroupId());
            ZuanshiBannerCrowdFindResponse rsp = client.execute(req, sessionKey);
            System.out.println("全店计划定向人群 : " + rsp.getBody());
            //"zuanshi_banner_crowd_find_response"
            JSONObject zsObject = JSON.parseObject(rsp.getBody());
            JSONObject onObject = zsObject.getJSONObject("zuanshi_banner_crowd_find_response");
            if (onObject == null) {
                continue;
            } else {
                //"result"
                JSONObject reObject = JSON.parseObject(onObject.toString());
                JSONObject twObject = reObject.getJSONObject("result");
                //"crowds"
                JSONObject crObject = JSON.parseObject(twObject.toString());
                JSONObject thOnject = crObject.getJSONObject("crowds");
                //判断是否为空
                if (thOnject == null) {
                    continue;
                } else {
                    // "crowd_d_t_o":[]
                    JSONObject dtObject = JSON.parseObject(thOnject.toString());
                    JSONArray dto = dtObject.getJSONArray("crowd_d_t_o");
                    //判断数组是否为空
                    if (dto == null) {
                        continue;//为空则直接返回
                    } else {
                        //List<CrowdList> crowdLists = JSONObject.parseArray(dto.toString(),CrowdList.class);
                        for (Object obb : dto.toArray()) {
                            JSONObject ob = JSONObject.parseObject(obb.toString());

                            //主营品牌人群
                            if (ob.getLong("crowd_type") == 128L){
                                MainstoreTargetList main = new MainstoreTargetList();
                                main.setTaobaoUserId(adl.getTaobaoUserId());
                                main.setCampaignId(ob.getLong("campaign_id"));
                                main.setAdgroupId(ob.getLong("adgroup_id"));
                                main.setCrowdName(ob.getString("crowd_name"));
                                main.setCrowdType(ob.getLong("crowd_type"));
                                main.setCrowdValue(ob.getString("crowd_value"));
                                main.setGmtCreate(ob.getDate("gmt_create"));
                                main.setGmtModified(ob.getDate("gmt_modified"));
                                main.setTargetId(ob.getLong("id"));
                                main.setMinPerSale(ob.getString("min_per_sale") == null ? "0" : ob.getString("min_per_sale"));
                                main.setMaxPerSale(ob.getString("max_per_sale") == null ? "0" : ob.getString("max_per_sale"));
                                main.setShopPreferenceValue(ob.getString("shop_preference_value") == null ? "0" : ob.getString("shop_preference_value"));
                                main.setCatId(ob.getLong("cat_id") == null ? 0L : ob.getLong("cat_id"));
                                main.setCatName(ob.getString("cat_name") == null ? "0" : ob.getString("cat_name"));

                                //"matrix_prices":{
                                JSONObject matrix = ob.getJSONObject("matrix_prices");
                                //"matrix_price_d_t_o":[
                                JSONObject mpd = JSONObject.parseObject(matrix.toString());
                                JSONArray pdto = mpd.getJSONArray("matrix_price_d_t_o");
                                if (pdto == null) {
                                    main.setAdzoneId(0L);
                                    main.setPrice(0L);
                                } else {
                                    for (Object mob : pdto.toArray()) {
                                        JSONObject mdto = JSONObject.parseObject(mob.toString());
                                        main.setAdzoneId(mdto.getLong("adzone_id") == null ? 0L : mdto.getLong("adzone_id"));
                                        main.setPrice(mdto.getLong("price") == null ? 0L : mdto.getLong("price"));
                                    }

                                }
                                //"sub_crowds":{
                                JSONObject sub = ob.getJSONObject("sub_crowds");
                                if (sub ==null){
                                    continue;
                                } else {
                                    JSONObject scd = JSONObject.parseObject(sub.toString());
                                    JSONArray cdto = scd.getJSONArray("sub_crowd_d_t_o");
                                    if (cdto == null) {
                                        main.setSubCrowdValue("0");
                                        main.setSubCrowdName("0");
                                    } else {
                                        for (Object sob : cdto.toArray()) {
                                            JSONObject subdto = JSONObject.parseObject(sob.toString());
                                            main.setSubCrowdName(subdto.getString("sub_crowd_name") == null ? "0" : subdto.getString("sub_crowd_name"));
                                            main.setSubCrowdValue(subdto.getString("sub_crowd_value") == null ? "0" : subdto.getString("sub_crowd_value"));
                                        }
                                    }
                                }
                                //"cat_id_list"
                                JSONArray catIdList = ob.getJSONArray("cat_id_list");
                                if (catIdList == null) {
                                    main.setCatIdList("0");
                                } else {
                                    String catId = catIdList.toString().substring(1, 3);
                                    main.setCatIdList(catId);
                                }
                                //"shop_scale_id_list"
                                JSONArray shopIdList = ob.getJSONArray("shop_scale_id_list");
                                if (shopIdList == null) {
                                    main.setShopScaleIdList("0");
                                } else {
                                    String shopId = shopIdList.toString().substring(1, 3);
                                    main.setShopScaleIdList(shopId);
                                }
                                mainstoreTargetListMapper.insert(main);
                            }

                            //获取喜欢相似宝贝的人群
                            if (ob.getLong("crowd_type") == 702L || ob.getLong("crowd_type") == 131072L){
                                SimlikeTargetList stl = new SimlikeTargetList();
                                stl.setTaobaoUserId(adl.getTaobaoUserId());
                                stl.setCampaignId(ob.getLong("campaign_id"));
                                stl.setAdgroupId(ob.getLong("adgroup_id"));
                                stl.setCrowdName(ob.getString("crowd_name"));
                                stl.setCrowdType(ob.getLong("crowd_type"));
                                stl.setCrowdValue(ob.getString("crowd_value"));
                                stl.setGmtCreate(ob.getDate("gmt_create"));
                                stl.setGmtModified(ob.getDate("gmt_modified"));
                                stl.setTargetId(ob.getLong("id"));
                                stl.setMinPerSale(ob.getString("min_per_sale") == null ? "0" : ob.getString("min_per_sale"));
                                stl.setMaxPerSale(ob.getString("max_per_sale") == null ? "0" : ob.getString("max_per_sale"));
                                stl.setShopPreferenceValue(ob.getString("shop_preference_value") == null ? "0" : ob.getString("shop_preference_value"));
                                stl.setCatId(ob.getLong("cat_id") == null ? 0L : ob.getLong("cat_id"));
                                stl.setCatName(ob.getString("cat_name") == null ? "0" : ob.getString("cat_name"));

                                //"matrix_prices":{
                                JSONObject matrix = ob.getJSONObject("matrix_prices");
                                //"matrix_price_d_t_o":[
                                JSONObject mpd = JSONObject.parseObject(matrix.toString());
                                JSONArray pdto = mpd.getJSONArray("matrix_price_d_t_o");
                                if (pdto == null) {
                                    stl.setAdzoneId(0L);
                                    stl.setPrice(0L);
                                } else {
                                    for (Object mob : pdto.toArray()) {
                                        JSONObject mdto = JSONObject.parseObject(mob.toString());
                                        stl.setAdzoneId(mdto.getLong("adzone_id") == null ? 0L : mdto.getLong("adzone_id"));
                                        stl.setPrice(mdto.getLong("price") == null ? 0L : mdto.getLong("price"));
                                    }

                                }
                                //"sub_crowds":{
                                JSONObject sub = ob.getJSONObject("sub_crowds");
                                if (sub ==null){
                                    continue;
                                } else {
                                    JSONObject scd = JSONObject.parseObject(sub.toString());
                                    JSONArray cdto = scd.getJSONArray("sub_crowd_d_t_o");
                                    if (cdto == null) {
                                        stl.setSubCrowdValue("0");
                                        stl.setSubCrowdName("0");
                                    } else {
                                        for (Object sob : cdto.toArray()) {
                                            JSONObject subdto = JSONObject.parseObject(sob.toString());
                                            stl.setSubCrowdName(subdto.getString("sub_crowd_name") == null ? "0" : subdto.getString("sub_crowd_name"));
                                            stl.setSubCrowdValue(subdto.getString("sub_crowd_value") == null ? "0" : subdto.getString("sub_crowd_value"));
                                        }
                                    }
                                }
                                //"cat_id_list"
                                JSONArray catIdList = ob.getJSONArray("cat_id_list");
                                if (catIdList == null) {
                                    stl.setCatIdList("0");
                                } else {
                                    String catId = catIdList.toString().substring(1, 3);
                                    stl.setCatIdList(catId);
                                }
                                //"shop_scale_id_list"
                                JSONArray shopIdList = ob.getJSONArray("shop_scale_id_list");
                                if (shopIdList == null) {
                                    stl.setShopScaleIdList("0");
                                } else {
                                    String shopId = shopIdList.toString().substring(1, 3);
                                    stl.setShopScaleIdList(shopId);
                                }
                                //simlikeTargetListMapper.insert(stl);
                            }

                            //获取全店计划定向-自主店铺
                            if (ob.getLong("crowd_type") == 16L) {
                                IndependentshopTargetList itl = new IndependentshopTargetList();
                                itl.setTaobaoUserId(adl.getTaobaoUserId());
                                itl.setCampaignId(ob.getLong("campaign_id"));
                                itl.setAdgroupId(ob.getLong("adgroup_id"));
                                itl.setCrowdName(ob.getString("crowd_name"));
                                itl.setCrowdType(ob.getLong("crowd_type"));
                                itl.setCrowdValue(ob.getString("crowd_value"));
                                itl.setGmtCreate(ob.getDate("gmt_create"));
                                itl.setGmtModified(ob.getDate("gmt_modified"));
                                itl.setTargetId(ob.getLong("id"));
                                itl.setMinPerSale(ob.getString("min_per_sale") == null ? "0" : ob.getString("min_per_sale"));
                                itl.setMaxPerSale(ob.getString("max_per_sale") == null ? "0" : ob.getString("max_per_sale"));
                                itl.setShopPreferenceValue(ob.getString("shop_preference_value") == null ? "0" : ob.getString("shop_preference_value"));
                                itl.setCatId(ob.getLong("cat_id") == null ? 0L : ob.getLong("cat_id"));
                                itl.setCatName(ob.getString("cat_name") == null ? "0" : ob.getString("cat_name"));

                                //"matrix_prices":{
                                JSONObject matrix = ob.getJSONObject("matrix_prices");
                                //"matrix_price_d_t_o":[
                                JSONObject mpd = JSONObject.parseObject(matrix.toString());
                                JSONArray pdto = mpd.getJSONArray("matrix_price_d_t_o");
                                if (pdto == null) {
                                    itl.setAdzoneId(0L);
                                    itl.setPrice(0L);
                                } else {
                                    for (Object mob : pdto.toArray()) {
                                        JSONObject mdto = JSONObject.parseObject(mob.toString());
                                        itl.setAdzoneId(mdto.getLong("adzone_id") == null ? 0L : mdto.getLong("adzone_id"));
                                        itl.setPrice(mdto.getLong("price") == null ? 0L : mdto.getLong("price"));
                                    }

                                }
                                //"sub_crowds":{
                                JSONObject sub = ob.getJSONObject("sub_crowds");
                                if (sub == null) {
                                    continue;
                                } else {
                                    JSONObject scd = JSONObject.parseObject(sub.toString());
                                    JSONArray cdto = scd.getJSONArray("sub_crowd_d_t_o");
                                    if (cdto == null) {
                                        itl.setSubCrowdValue("0");
                                        itl.setSubCrowdName("0");
                                    } else {
                                        for (Object sob : cdto.toArray()) {
                                            JSONObject subdto = JSONObject.parseObject(sob.toString());
                                            itl.setSubCrowdName(subdto.getString("sub_crowd_name") == null ? "0" : subdto.getString("sub_crowd_name"));
                                            itl.setSubCrowdValue(subdto.getString("sub_crowd_value") == null ? "0" : subdto.getString("sub_crowd_value"));
                                        }
                                    }
                                }
                                //"cat_id_list"
                                JSONArray catIdList = ob.getJSONArray("cat_id_list");
                                if (catIdList == null) {
                                    itl.setCatIdList("0");
                                } else {
                                    String catId = catIdList.toString().substring(1, 3);
                                    itl.setCatIdList(catId);
                                }
                                //"shop_scale_id_list"
                                JSONArray shopIdList = ob.getJSONArray("shop_scale_id_list");
                                if (shopIdList == null) {
                                    itl.setShopScaleIdList("0");
                                } else {
                                    String shopId = shopIdList.toString().substring(1, 3);
                                    itl.setShopScaleIdList(shopId);
                                }
                                //independentshopTargetListMapper.insert(itl);
                            }

                            //获取喜欢相似宝贝的人群
                            //if (ob.getString("crowd_name").equals("喜欢相似宝贝的人群"))
                            if (ob.getLong("crowd_type") == 262144L) {
                                MylikeTargetList mtl = new MylikeTargetList();
                                mtl.setTaobaoUserId(adl.getTaobaoUserId());
                                mtl.setCampaignId(ob.getLong("campaign_id"));
                                mtl.setAdgroupId(ob.getLong("adgroup_id"));
                                mtl.setCrowdName(ob.getString("crowd_name"));
                                mtl.setCrowdType(ob.getLong("crowd_type"));
                                mtl.setCrowdValue(ob.getString("crowd_value"));
                                mtl.setGmtCreate(ob.getDate("gmt_create"));
                                mtl.setGmtModified(ob.getDate("gmt_modified"));
                                mtl.setTargetId(ob.getLong("id"));
                                mtl.setMinPerSale(ob.getString("min_per_sale") == null ? "0" : ob.getString("min_per_sale"));
                                mtl.setMaxPerSale(ob.getString("max_per_sale") == null ? "0" : ob.getString("max_per_sale"));
                                mtl.setShopPreferenceValue(ob.getString("shop_preference_value") == null ? "0" : ob.getString("shop_preference_value"));
                                mtl.setCatId(ob.getLong("cat_id") == null ? 0L : ob.getLong("cat_id"));
                                mtl.setCatName(ob.getString("cat_name") == null ? "0" : ob.getString("cat_name"));
                                //"matrix_prices":{
                                JSONObject matrix = ob.getJSONObject("matrix_prices");
                                //"matrix_price_d_t_o":[
                                JSONObject mpd = JSONObject.parseObject(matrix.toString());
                                JSONArray pdto = mpd.getJSONArray("matrix_price_d_t_o");
                                if (pdto == null) {
                                    mtl.setAdzoneId(0L);
                                    mtl.setPrice(0L);
                                } else {
                                    for (Object mob : pdto.toArray()) {
                                        JSONObject mdto = JSONObject.parseObject(mob.toString());
                                        mtl.setAdzoneId(mdto.getLong("adzone_id") == null ? 0L : mdto.getLong("adzone_id"));
                                        mtl.setPrice(mdto.getLong("price") == null ? 0L : mdto.getLong("price"));
                                    }
                                }
                                //"sub_crowds":{
                                JSONObject sub = ob.getJSONObject("sub_crowds");
                                if (sub == null) {
                                    continue;
                                } else {
                                    JSONObject scd = JSONObject.parseObject(sub.toString());
                                    JSONArray cdto = scd.getJSONArray("sub_crowd_d_t_o");
                                    if (cdto == null) {
                                        mtl.setSubCrowdValue("0");
                                        mtl.setSubCrowdName("0");
                                    } else {
                                        for (Object sob : cdto.toArray()) {
                                            JSONObject subdto = JSONObject.parseObject(sob.toString());
                                            mtl.setSubCrowdName(subdto.getString("sub_crowd_name") == null ? "0" : subdto.getString("sub_crowd_name"));
                                            mtl.setSubCrowdValue(subdto.getString("sub_crowd_value") == null ? "0" : subdto.getString("sub_crowd_value"));
                                        }
                                    }

                                    //"cat_id_list"
                                    JSONArray catIdList = ob.getJSONArray("cat_id_list");
                                    if (catIdList == null) {
                                        mtl.setCatIdList("0");
                                    } else {
                                        String catId = catIdList.toString().substring(1, 3);
                                        mtl.setCatIdList(catId);
                                    }
                                    //"shop_scale_id_list"
                                    JSONArray shopIdList = ob.getJSONArray("shop_scale_id_list");
                                    if (shopIdList == null) {
                                        mtl.setShopScaleIdList("0");
                                    } else {
                                        String shopId = shopIdList.toString().substring(1, 3);
                                        mtl.setShopScaleIdList(shopId);
                                    }
                                    //mylikeTargetListMapper.insert(mtl);
                                }
                            }

                            //获取全店计划定向人群
                            CrowdList cl = new CrowdList();
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
                            if (sub == null) {
                                continue;
                            } else {
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
                            }
                            //"cat_id_list"
                            JSONArray catIdList = ob.getJSONArray("cat_id_list");
                            if (catIdList == null) {
                                cl.setCatIdList("0");
                            } else {
                                String catId = catIdList.toString().substring(1, 3);
                                cl.setCatIdList(catId);
                            }
                            //"shop_scale_id_list"
                            JSONArray shopIdList = ob.getJSONArray("shop_scale_id_list");
                            if (shopIdList == null) {
                                cl.setShopScaleIdList("0");
                            } else {
                                String shopId = shopIdList.toString().substring(1, 3);
                                cl.setShopScaleIdList(shopId);
                            }
                            //crowdListMapper.insert(cl);
                        }
                    }
                }
            }
        }
        return "";
    }
}
