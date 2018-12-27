package com.juzuan.advertiser.rpts.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration //做spring的注容器，创建tomcat，开spring的加载，然后类就都可以使用了
@RestController //表示该接口全部返回json格式  相当于下面写了一个@ResponseBody
@RequestMapping(value = "/juzuan")
public class HellpWorldController {
    @RequestMapping("/index")
    public String index(){
        String abc = "ok";
        return abc;
    }
    @RequestMapping("/hello")
    public String hello(){

        return "你好";
    }
    @RequestMapping("/get")
    public Map<String,Object> getMap(){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("errorCode","250");
        result.put("errorMap","成功");
        return result;
    }
    public  static void main(String[] args){
        SpringApplication.run(HellpWorldController.class,args);
    }
}

