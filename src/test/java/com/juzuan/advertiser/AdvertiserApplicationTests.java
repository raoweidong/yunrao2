package com.juzuan.advertiser;

import com.juzuan.advertiser.rpts.mapper.AdvertiserAccountRptsDayGetMapper;
import com.juzuan.advertiser.rpts.model.AdvertiserAccountRptsDayGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertiserApplicationTests {

    @Autowired
    private AdvertiserAccountRptsDayGetMapper advertiserAccountRptsDayGetMapper;

    @Test
    public void test() {

    }

}
