package com.juzuan.advertiser.rpts.controller;

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
    @RequestMapping(value = "tongbuTables")
    public String tongbuTables(){
        synchronizeTablesService.synchronize();
        return "同步成功";
    }

}
