package com.juzuan.advertiser.rpts.controller;

import com.juzuan.advertiser.rpts.model.AdzoneListBindUpdate;
import com.juzuan.advertiser.rpts.service.AdzoneListBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@EnableAutoConfiguration
@RestController
@RequestMapping("/juzuan")
public class AdzoneListBindController {
    @Autowired
    private AdzoneListBindService adzoneListBindService;

    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public AdzoneListBindUpdate update(String taobaoUserId, Long campaignId, Long adgroupId){
        AdzoneListBindUpdate adzoneListBindUpdate =new AdzoneListBindUpdate();

        String status =  adzoneListBindService.update(taobaoUserId,campaignId,adgroupId);
        System.out.println(status);
        if (status.equals("200")){
            adzoneListBindUpdate.setAdgroupId(adgroupId);
            adzoneListBindUpdate.setCampaignId(campaignId);
            adzoneListBindUpdate.setTaobaoUserId(taobaoUserId);
            System.out.println("0");
        }else {
            System.out.println("1");
        }

        return adzoneListBindUpdate;
    }
    public  static void main(String[] args){
        SpringApplication.run(AdzoneListBindController.class,args);
    }
}
