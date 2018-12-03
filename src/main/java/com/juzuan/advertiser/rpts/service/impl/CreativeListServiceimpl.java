package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CreativeListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CreativeList;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CreativeListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCreativeFindRequest;
import com.taobao.api.response.ZuanshiBannerCreativeFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreativeListServiceimpl implements CreativeListService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private CreativeListMapper creativeListMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    public String CreativeList(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
                TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
                ZuanshiBannerCreativeFindRequest req = new ZuanshiBannerCreativeFindRequest();
                req.setPageSize(100L);
                ZuanshiBannerCreativeFindResponse rsp = null;
                try {
                    rsp = client.execute(req,tau.getAccessToken());
                } catch (ApiException e){
                    e.printStackTrace();
                }
                System.out.println("创意列表JSON : "+rsp.getBody());

                JSONObject one = JSON.parseObject(rsp.getBody());
                //"zuanshi_banner_creative_find_response"
                JSONObject cfr = one.getJSONObject("zuanshi_banner_creative_find_response");
                //"result"
                JSONObject two = JSON.parseObject(cfr.toString());
                JSONObject res = two.getJSONObject("result");
                //"creatives"
                JSONObject thr = JSON.parseObject(res.toString());
                JSONObject cre = thr.getJSONObject("creatives");
                //"creative":[]
                JSONObject fou = JSON.parseObject(cre.toString());
                JSONArray creat = fou.getJSONArray("creative");
                if (creat == null){
                    continue;
                } else {
                    for (Object ob: creat.toArray()) {
                        CreativeList cl = new CreativeList();
                        JSONObject aa=JSON.parseObject(ob.toString());
                        cl.setTaobaoUserId(tau.getTaobaoUserId());
                        cl.setCreativeId(aa.getLong("id"));
                        cl.setCreativeName(aa.getString("name"));
                        cl.setAuditStatus(aa.getInteger("audit_status"));
                        cl.setCatId(aa.getString("cat_id"));
                        cl.setCatName(aa.getString("cat_name"));
                        cl.setClickUrl(aa.getString("click_url")==null?"0":aa.getString("click_url"));
                        cl.setCreativeLevel(aa.getInteger("creative_level"));
                        JSONObject bb=aa.getJSONObject("creative_size");
                        String size = bb.getString("height")+"*"+ bb.getString("width");
                        cl.setCreativeSize(size);
                        cl.setExpireTime(aa.getString("expire_time")==null?"0":aa.getString("expire_time"));
                        cl.setFormat(aa.getInteger("format"));
                        cl.setCreateTime(aa.getDate("create_time"));
                        cl.setModifiedTime(aa.getDate("modified_time"));
                        cl.setImagePath(aa.getString("image_path")==null?"0":aa.getString("image_path"));
                        cl.setPackageType(aa.getInteger("package_type"));
                        creativeListMapper.insert(cl);
                    }
                }

        }
        return "";
    }

}
