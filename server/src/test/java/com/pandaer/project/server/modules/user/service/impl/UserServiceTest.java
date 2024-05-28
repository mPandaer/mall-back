package com.pandaer.project.server.modules.user.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.BootApplication;
import com.pandaer.project.server.modules.user.po.AddUserPO;
import com.pandaer.project.server.modules.user.po.PageQueryUserPO;
import com.pandaer.project.server.modules.user.po.PageSearchUserPO;
import com.pandaer.project.server.modules.user.po.UpdateUserPO;
import com.pandaer.project.server.modules.user.service.UserService;
import com.pandaer.project.server.modules.user.vo.UserVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest(classes = BootApplication.class)
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        AddUserPO addUserPO = new AddUserPO();
        addUserPO.setUsername("pandaer");
        addUserPO.setPassword("101807");
        addUserPO.setEmail("1111@qq.cm");
        userService.addUser(addUserPO);
    }

    @Test
    void testUpdateUser() {
        UpdateUserPO updateUserPO = new UpdateUserPO();
        updateUserPO.setUserId(1790235708695777280L);
        updateUserPO.setIsEnable(1);

        userService.updateUser(updateUserPO);

    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(Collections.singletonList(1790235708695777280L));
    }

    @Test
    void testPageUser() {
        PageQueryUserPO pageQueryUserPO = new PageQueryUserPO();
        pageQueryUserPO.setPageSize(10L);
        pageQueryUserPO.setCurrentPage(1L);
        Page<UserVO> userVOPage = userService.pageUser(pageQueryUserPO);
        System.out.println(JSONUtil.toJsonPrettyStr(userVOPage));


    }

    @Test
    void testGetUserById() {
        UserVO vo = userService.getUserBy(1790235708695777280L);
        System.out.println(vo);
    }

    @Test
    void testGetUserByName() {
        PageSearchUserPO pageSearchUserPO = new PageSearchUserPO();
        pageSearchUserPO.setPageSize(10L);
        pageSearchUserPO.setCurrentPage(1L);
        pageSearchUserPO.setName("pan");

        Page<UserVO> userBy = userService.pageSearchUser(pageSearchUserPO);
        System.out.println(JSONUtil.toJsonPrettyStr(userBy));
    }

    @Test
    void testResetPassword() {
        userService.resetPassword(1790235708695777280L);
    }
}