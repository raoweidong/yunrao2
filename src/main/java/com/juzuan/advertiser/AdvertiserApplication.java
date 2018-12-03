package com.juzuan.advertiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdvertiserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertiserApplication.class, args);
    }
}
