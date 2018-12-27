package com.juzuan.advertiser.rpts.controller;

import com.juzuan.advertiser.rpts.mapper.TaobaoAuthorizeUserMapper;
import com.juzuan.advertiser.rpts.model.Response;
import com.juzuan.advertiser.rpts.model.TaobaoAuthorizeUser;
import com.juzuan.advertiser.rpts.service.SynchronizeTablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class SynchronizeTablesController {
    @Autowired
    private SynchronizeTablesService synchronizeTablesService;
    @Autowired
    private TaobaoAuthorizeUserMapper taobaoAuthorizeUserMapper;
    @RequestMapping(value = "tongbuTables")
    public Response tongbuTables(String userId){
        TaobaoAuthorizeUser taobaoAuthorizeUser=taobaoAuthorizeUserMapper.slectByUserId(userId);
        Response response=new Response();
        if (taobaoAuthorizeUser!=null){
            String  chuShiShuJu= synchronizeTablesService.synchronize(userId);
            response.setCode(0);
            response.setMsg("success");
        }else {
            response.setCode(1);
            response.setMsg("非法参数");
        }


        return response;
    }

}
