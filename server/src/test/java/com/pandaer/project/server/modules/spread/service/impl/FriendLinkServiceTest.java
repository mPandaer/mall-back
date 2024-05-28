package com.pandaer.project.server.modules.spread.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.project.server.modules.spread.po.AddFriendLinkPO;
import com.pandaer.project.server.modules.spread.po.PageQueryFriendLinkPO;
import com.pandaer.project.server.modules.spread.po.UpdateFriendLinkPO;
import com.pandaer.project.server.modules.spread.service.FriendLinkService;
import com.pandaer.project.server.modules.spread.vo.FriendLinkVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendLinkServiceTest {

    @Resource
    private FriendLinkService friendLinkService;

    @Test
    void addFriendLink() {
        AddFriendLinkPO addFriendLinkPO = new AddFriendLinkPO();
        addFriendLinkPO.setLinkName("京东");
        addFriendLinkPO.setLinkUrl("http://jd.com");
        addFriendLinkPO.setLinkImgUrl("http://jd.com/hello.png");

        friendLinkService.addFriendLink(addFriendLinkPO);

    }

    @Test
    void updateFriendLink() {
        UpdateFriendLinkPO updateFriendLinkPO = new UpdateFriendLinkPO();
        updateFriendLinkPO.setLinkId(1792844103131172864L);
        updateFriendLinkPO.setLinkName("京东2号");
        updateFriendLinkPO.setLinkUrl(null);
        updateFriendLinkPO.setLinkImgUrl(null);

        friendLinkService.updateFriendLink(updateFriendLinkPO);

    }

    @Test
    void deleteFriendLink() {
        friendLinkService.deleteFriendLink("1792846811372199936");
    }

    @Test
    void pageQueryFriendLink() {
        PageQueryFriendLinkPO pageQueryFriendLinkPO = new PageQueryFriendLinkPO();
        pageQueryFriendLinkPO.setPageSize(10);
        pageQueryFriendLinkPO.setCurrentPage(1);

        IPage<FriendLinkVO> friendLinkVOIPage = friendLinkService.pageQueryFriendLink(pageQueryFriendLinkPO);
        System.out.println(JSONUtil.toJsonPrettyStr(friendLinkVOIPage));


    }
}