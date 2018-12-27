package com.juzuan.advertiser;

import com.juzuan.advertiser.rpts.mapper.CampaignListMapper;
import com.juzuan.advertiser.rpts.model.CampaignList;
import com.juzuan.advertiser.rpts.query.UserAndId;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdvertiserApplication.class)
public class Test {
    @Autowired
    private CampaignListMapper campaignListMapper;
    @org.junit.Test
    public void tt(){
        UserAndId userAndId=new UserAndId();
        userAndId.setTaobaoUserId("3075039900");
        userAndId.setCampaignId(431263654L);
       CampaignList campaignList= campaignListMapper.selectByUserAndId(userAndId);
        System.out.println( campaignList.getCampaignName());
        List<CampaignList> campaignLists= campaignListMapper.selectByUserId("3075039900");
        for (CampaignList campaignLis:campaignLists){
            System.out.println(campaignLis.getCampaignName());
        }
       campaignListMapper.deleteAll();

    }
}
