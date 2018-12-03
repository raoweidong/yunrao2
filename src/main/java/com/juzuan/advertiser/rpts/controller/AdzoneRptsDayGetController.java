package com.juzuan.advertiser.rpts.controller;

import com.juzuan.advertiser.rpts.mapper.AdvertiserAdzoneRptsDayGetMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsDayGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ResponseBody
@Controller
public class AdzoneRptsDayGetController {
    @Autowired
    private AdvertiserAdzoneRptsDayGetMapper advertiserAdzoneRptsDayGetMapper;
    @RequestMapping(value = "/adzonesDate",produces ={"application/json;charset=UTF-8"},method = RequestMethod.POST)
    public List<AdvertiserAdzoneRptsDayGet> queryAdzoneRptsDayGets(){

        Map<String,Object> data=new HashMap();
        data.put("currIndex",10);
        data.put("pageSize",10);

        return advertiserAdzoneRptsDayGetMapper.queryAdzoneRptsDayGetsBySql(data);
    }
}
