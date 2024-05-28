package com.pandaer.project.server.modules.spread.service.impl;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.server.modules.spread.po.AddADPO;
import com.pandaer.project.server.modules.spread.po.PageQueryADPO;
import com.pandaer.project.server.modules.spread.po.UpdateADPO;
import com.pandaer.project.server.modules.spread.service.AdvertisementService;
import com.pandaer.project.server.modules.spread.vo.ADVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertisementServiceTest {

    @Resource
    private AdvertisementService advertisementService;

    @Test
    void addAD() {
        AddADPO addADPO = new AddADPO();
        addADPO.setCompanyName("京东");
        addADPO.setAdCost(new BigDecimal("99"));
        addADPO.setAdValidTime(LocalDateTime.now());
        addADPO.setAdUrl("http://hello.png");
        addADPO.setAdImgUrl("http://hello.jpg");

        advertisementService.addAD(addADPO);

    }

    @Test
    void updateAD() {
        UpdateADPO updateADPO = new UpdateADPO();
        updateADPO.setAdId(1792864963435016192L);
        updateADPO.setCompanyName("京东小号");

        advertisementService.updateAD(updateADPO);


    }

    @Test
    void deleteAD() {
        advertisementService.deleteAD("1792864963435016192");
    }

    @Test
    void pageQueryAD() {

        PageQueryADPO pageQueryADPO = new PageQueryADPO();
        pageQueryADPO.setCurrentPage(1);
        pageQueryADPO.setPageSize(10);

        IPage<ADVO> advoiPage = advertisementService.pageQueryAD(pageQueryADPO);
        System.out.println(JSONUtil.toJsonPrettyStr(advoiPage));


    }


    @Test
    void demo() {
        System.out.println(ObjectUtil.equal(1L, 1));
    }
}