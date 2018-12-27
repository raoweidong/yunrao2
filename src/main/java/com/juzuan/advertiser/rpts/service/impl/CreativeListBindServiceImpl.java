package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AdgroupListMapper;
import com.juzuan.advertiser.rpts.mapper.CreativeListBindMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AdgroupList;
import com.juzuan.advertiser.rpts.model.CreativeListBind;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.CreativeListBindService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCreativeFindBindRequest;
import com.taobao.api.response.ZuanshiBannerCreativeFindBindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreativeListBindServiceImpl implements CreativeListBindService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AdgroupListMapper adgroupListMapper;
    @Autowired
    private CreativeListBindMapper creativeListBindMapper;

    //@Scheduled(cron = "*/5 * * * * ?")
    @Override
    public String CreativeListBind(){
        List<AdgroupList> adgroupLists = adgroupListMapper.selectAllAdgroup();
        for (AdgroupList al: adgroupLists) {
            TaobaoAuthorizeUser taobaoAuthorizeUser = taobaoAuthorizeUserMapper.slectByUserId(al.getTaobaoUserId());
            String sessionKey = taobaoAuthorizeUser.getAccessToken();
            TaobaoClient client = new DefaultTaobaoClient(url,appkey,secret);
            ZuanshiBannerCreativeFindBindRequest req = new ZuanshiBannerCreativeFindBindRequest();
            req.setCampaignId(al.getCampaignId());
            req.setAdgroupId(al.getAdgroupId());
            ZuanshiBannerCreativeFindBindResponse rsp = null;
            try{
                rsp = client.execute(req,sessionKey);
            } catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("已绑定的创意json : "+rsp.getBody());

            //"zuanshi_banner_creative_find_bind_response"
            JSONObject first = JSON.parseObject(rsp.getBody());
            JSONObject bcfbr = first.getJSONObject("zuanshi_banner_creative_find_bind_response");
            //"result"
            JSONObject second = JSON.parseObject(bcfbr.toString());
            JSONObject result = second.getJSONObject("result");
            //"creatives"
            JSONObject third = JSON.parseObject(result.toString());
            JSONObject crtea = third.getJSONObject("creatives");
            //"creative":[]
            JSONObject fourth = JSON.parseObject(crtea.toString());
            JSONArray creative = fourth.getJSONArray("creative");
            if (creative == null){
                continue;
            } else {
                for (Object ob: creative.toArray()) {
                    CreativeListBind clb = new CreativeListBind();
                    JSONObject ctv = JSON.parseObject(ob.toString());
                    clb.setTaobaoUserId(al.getTaobaoUserId());
                    clb.setCampaignId(al.getCampaignId());
                    clb.setAdgroupId(al.getAdgroupId());
                    clb.setCreativeId(ctv.getLong("id"));
                    clb.setCreativeName(ctv.getString("creative_name")==null?"0":ctv.getString("creative_name"));
                    clb.setAuditStatus(ctv.getInteger("audit_status"));
                    clb.setCatId(ctv.getString("cat_id"));
                    clb.setCatName(ctv.getString("cat_name"));
                    clb.setClickUrl(ctv.getString("click_url")==null?"0":ctv.getString("click_url"));
                    JSONObject sizeObject = ctv.getJSONObject("creative_size");
                    String size = sizeObject.getString("height")+"*"+sizeObject.getString("width");
                    clb.setCreativeSize(size);
                    clb.setExpireTime(ctv.getString("expire_time"));
                    clb.setFormat(ctv.getInteger("format"));
                    clb.setCreativeLevel(ctv.getInteger("creative_level"));
                    clb.setCreateTime(ctv.getDate("create_time"));
                    clb.setModifiedTime(ctv.getDate("modified_time"));
                    clb.setImagePath(ctv.getString("image_path"));
                    clb.setPackageType(ctv.getInteger("package_type"));
                    creativeListBindMapper.insert(clb);
                }
            }
        }
        return "";
    }


}
