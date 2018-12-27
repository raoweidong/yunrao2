package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.model.Response;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.query.UserAndId;
import com.juzuan.advertiser.rpts.service.CampaignListService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiBannerCampaignFindRequest;
import com.taobao.api.response.ZuanshiBannerCampaignFindResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignListServiceImpl implements CampaignListService {

    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private  CampaignListMapper campaignListMapper;
    //前端同步计划数据
    @Override
    public Response requestCampaignList(String userId){
        campaignListMapper.deleteAll();//删除之前的数据
        TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.slectByUserId(userId);
        String sessionKey=taobaoAuthorizeUser.getAccessToken();
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        ZuanshiBannerCampaignFindRequest req = new ZuanshiBannerCampaignFindRequest();
        ZuanshiBannerCampaignFindResponse rsp = null;
        try {
            rsp = client.execute(req, sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        //parseCampaign(rsp.getBody(),taobaoAuthorizeUser);//解析保存
        String jieGuo=parseCampaign(rsp.getBody(),taobaoAuthorizeUser);
        Response response=new Response();
        if (jieGuo=="解析成功"){
            response.setCode(0);
            response.setMsg("成功");
        }else{
            response.setCode(1);
            response.setMsg("不合法参数");
        }

        return  response;
    }

   //定时任务
   //@Scheduled(cron = "*/5 * * * * ?")// 0 0 8,14,16 * * ? 每天上午8点，下午2点，4点   */5 * * * * ?五秒钟运行一次.
   @Override
    public String campaignList(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers=taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser taobaoAuthorizeUser:taobaoAuthorizeUsers){
           /* TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.selectByPrimaryKey(1L);
            String sessionKey=taobaoAuthorizeUser.getAccessToken();*/
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiBannerCampaignFindRequest req = new ZuanshiBannerCampaignFindRequest();
            ZuanshiBannerCampaignFindResponse rsp = null;
            req.setPageSize(100L);
            try {
                rsp = client.execute(req, taobaoAuthorizeUser.getAccessToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());
            parseCampaign(rsp.getBody(),taobaoAuthorizeUser);
        }

        return "";
    }

    public String parseCampaign(String json,TaobaoAuthorizeUser taobaoAuthorizeUser){
        JSONObject onObject= JSON.parseObject(json);
        JSONObject oneObject=onObject.getJSONObject("zuanshi_banner_campaign_find_response");
        System.out.println(oneObject.toString());

        JSONObject twObject=JSON.parseObject(oneObject.toString());
        JSONObject twoObject=twObject.getJSONObject("result");
        System.out.println(twoObject.toString());


        JSONObject threObject=JSON.parseObject(twoObject.toString());
        JSONObject threeObject=threObject.getJSONObject("campaigns");

        JSONObject fouObject=JSONObject.parseObject(threeObject.toString());
        JSONArray fourObject=fouObject.getJSONArray("campaign");
       // TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.selectByPrimaryKey(1L);//haha
        for (Object ob:fourObject.toArray()){
           // TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.selectByPrimaryKey(1L);
            //String sessionKey=taobaoAuthorizeUser.getAccessToken();
            CampaignList campaignList=new CampaignList();
            System.out.println("遍历打印计划  "+ob.toString());
            JSONObject obb=JSONObject.parseObject(ob.toString());

            JSONObject propertie=obb.getJSONObject("properties");
            System.out.println("最后  "+propertie.toString());
            campaignList.setMarketingdemand(propertie.getString("marketingdemand")==null?"0":propertie.getString("marketingdemand"));//计划类型：-1：自定义，1：日常托管，2：日常推荐，3：拉新托管，4：拉新推荐',
            System.out.println( "得到的计划id "+ obb.getInteger("id"));
            Long campId=obb.getLong("id");
            campaignList.setCampaignId(campId);//计划id
            campaignList.setCampaignName(obb.getString("name"));//计划名
            campaignList.setStartTime(obb.getDate("start_time"));//开始时间
            campaignList.setEndTime(obb.getDate("end_time"));//结束时间
            campaignList.setDayBudget(obb.getDouble("day_budget"));//日预算
            campaignList.setOnlineStatus(obb.getInteger("online_status"));//计划状态
            campaignList.setSpeedType(obb.getInteger("speed_type"));//投放方式
            campaignList.setLifeCycle(obb.getString("life_cycle"));//计划编辑形式
            campaignList.setCampaignType(obb.getInteger("type"));//计划类型

            JSONObject propert=obb.getJSONObject("properties");
            JSONObject bannerTime=obb.getJSONObject("banner_time");
            JSONObject workdayOb=JSONObject.parseObject(bannerTime.toString());
            //第一个boolean
            JSONObject workdayObj=workdayOb.getJSONObject("workdays");
           JSONObject booleaObject=JSONObject.parseObject(workdayObj.toString());
           JSONArray booleaObb=booleaObject.getJSONArray("boolean");
            String works=booleaObb.toString().substring(1,121);
            campaignList.setWorkdays(works);//添加workdays数据
            //weekEnds
            JSONObject weekEnds=workdayOb.getJSONObject("week_ends");
            JSONObject tB=JSONObject.parseObject(weekEnds.toString());
            JSONArray twoBoolean=tB.getJSONArray("boolean");
            String weekEn=twoBoolean.toString().substring(1,121);
            campaignList.setWeekEnds(weekEn);//添加weekends数据
            campaignList.setTaobaoUserId(taobaoAuthorizeUser.getTaobaoUserId());
            //______________________________________________
            System.out.println("接受的第一个数据 "+campaignList.getCampaignId());
            //先查询再插入 有重复的数据跳过
            UserAndId lianGe=new UserAndId();
            lianGe.setTaobaoUserId(campaignList.getTaobaoUserId());
            lianGe.setCampaignId(campaignList.getCampaignId());
            CampaignList campaignListt=campaignListMapper.selectByUserAndId(lianGe);
            if (campaignListt==campaignList){
                continue;  //有重复的数据跳过
            }else{campaignListMapper.insert(campaignList);}
           //campaignListMapper.insert(campaignList);
           /* List<CampaignList> campp=selectAllCampaign();
            for (CampaignList camm:campp){
                System.out.println(camm.getCampaignName());
            }*/

        }
    return "解析成功";
    }

    @Override
    public int insert(CampaignList record) {
        return 0;
    }
}
