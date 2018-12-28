package com.juzuan.advertiser.rpts.controller;

import com.juzuan.advertiser.rpts.mapper.AdvertiserAdzoneRptsDayGetMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAdzoneRptsDayGet;
import com.juzuan.advertiser.rpts.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdzoneRptsDayGetController {
    @Autowired
    private AdvertiserAdzoneRptsDayGetMapper advertiserAdzoneRptsDayGetMapper;
    @ResponseBody
    @RequestMapping(value = "/adzonesDate",produces ={"application/json;charset=UTF-8"})
    public Page<AdvertiserAdzoneRptsDayGet> findWithPage(int pageNum, int pageSize){
        Map<String,Object> data=new HashMap<>();
        data.put("startIndex",(pageNum-1)*pageSize);
        data.put("pageSize",pageSize);
        List<AdvertiserAdzoneRptsDayGet> ss=advertiserAdzoneRptsDayGetMapper.queryAdzoneRptsDayGetsBySql(data);
        int totalRecord=advertiserAdzoneRptsDayGetMapper.countByNull();
           Page pb=new Page(pageNum,pageSize,totalRecord);
        pb.setTotalRecord(totalRecord);
        pb.setList(ss);
        System.out.println("哈哈");


        return pb;
    }
}
