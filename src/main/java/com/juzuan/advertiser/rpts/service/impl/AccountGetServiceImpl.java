package com.juzuan.advertiser.rpts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.juzuan.advertiser.rpts.mapper.AccountGetMapper;
import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.AccountGet;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.AccountGetService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ZuanshiAccountGetRequest;
import com.taobao.api.response.ZuanshiAccountGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *获取账户信息接口
 */
@Service
public class AccountGetServiceImpl implements AccountGetService {
    private static String appkey="25139411";
    private static String url ="https://eco.taobao.com/router/rest";
    private static String secret="ccd188d30d3731df6d176ba8a2151765";

    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @Autowired
    private AccountGetMapper accountGetMapper;
    //@Scheduled(cron = "*/5 * * * * ?")
    public String AccountGet(){
        List<TaobaoAuthorizeUser> taobaoAuthorizeUsers = taobaoAuthorizeUserMapper.selectAllToken();
        for (TaobaoAuthorizeUser tau: taobaoAuthorizeUsers) {
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            ZuanshiAccountGetRequest req = new ZuanshiAccountGetRequest();
            ZuanshiAccountGetResponse rsp = null;
            try {
                rsp = client.execute(req,tau.getAccessToken());
            }catch (ApiException e){
                e.printStackTrace();
            }
            System.out.println("获取账户信息接口 : "+rsp.getBody());
            //解析json
            JSONObject oneObject = JSON.parseObject(rsp.getBody());
            JSONObject zuanshi = oneObject.getJSONObject("zuanshi_account_get_response");
            JSONObject twoObject = JSON.parseObject(zuanshi.toString());
            JSONObject result = twoObject.getJSONObject("result");
            JSONObject thrObject = JSON.parseObject(result.toString());
            JSONObject account = thrObject.getJSONObject("account");
            JSONObject object = JSON.parseObject(account.toString());
            AccountGet accountGet = new AccountGet();
            accountGet.setTaobaoUserId(tau.getTaobaoUserId());
            accountGet.setBanlance(object.getString("banlance"));
            accountGet.setGrantBalance(object.getString("grant_balance"));
            accountGet.setCashBalance(object.getString("cash_balance"));
            accountGet.setAvailableBalance(object.getString("available_balance"));
            accountGet.setCreditBalance(object.getString("credit_balance"));
            accountGet.setRedPacket(object.getString("red_packet"));
            accountGetMapper.insert(accountGet);
        }
        return "";
    }
}
