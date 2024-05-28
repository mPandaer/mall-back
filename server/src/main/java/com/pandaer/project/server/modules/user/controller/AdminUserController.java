package com.pandaer.project.server.modules.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.common.auth.AuthService;
import com.pandaer.project.server.common.auth.entity.TokenEntity;
import com.pandaer.project.server.modules.user.po.*;
import com.pandaer.project.server.modules.user.service.UserService;
import com.pandaer.project.server.modules.user.vo.LoginUserVO;
import com.pandaer.project.server.modules.user.vo.UserVO;
import com.pandaer.project.server.util.LoginIdUtil;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户管理模块", description = "提供用户的增删改查的能力")
public class AdminUserController {

    @Resource
    private UserService userService;

    @Resource
    private AuthService authService;

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public Resp<Object> addUser(@Validated @RequestBody AddUserPO po) {
        userService.addUser(po);
        return Resp.success();
    }

    @Operation(summary = "修改用户")
    @PostMapping("/update")
    public Resp<Object> updateUser(@Validated @RequestBody UpdateUserPO po) {
        userService.updateUser(po);
        return Resp.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/delete")
    public Resp<Object> deleteUser(@NotEmpty(message = "删除的Id列表不能为空") @RequestParam("idList") String idListStr) {
        List<Long> list = Arrays.stream(idListStr.split(",")).map(Long::valueOf).toList();
        userService.deleteUser(list);
        return Resp.success();
    }

    @Operation(summary = "分页查询用户")
    @GetMapping("/page/query")
    public Resp<Page<UserVO>> pageQueryUser(@Validated PageQueryUserPO po) {
        Page<UserVO> voPage = userService.pageUser(po);
        return Resp.success(voPage);
    }

    @Operation(summary = "分页搜索用户")
    @GetMapping("/page/search")
    public Resp<Page<UserVO>> pageSearchUser(@Validated PageSearchUserPO po) {
        Page<UserVO> voPage = userService.pageSearchUser(po);
        return Resp.success(voPage);
    }

    @Operation(summary = "根据用户ID查询单个用户")
    @GetMapping("/query/{id}")
    public Resp<UserVO> findUserBy(@NotNull(message = "用户ID不能为空") @Min(value = 1, message = "用户ID不合法") @PathVariable("id") Long id) {
        UserVO userVO = userService.getUserBy(id);
        return Resp.success(userVO);
    }

    @Operation(summary = "根据用户ID重置密码")
    @GetMapping("/reset/password/{id}")
    public Resp<Object> resetPassword(@PathVariable("id") Long userId) {
        userService.resetPassword(userId);
        return Resp.success();
    }


    /**
     * 用户头像上传
     */
    @PostMapping("/upload-avatar")
    public Resp<String> uploadAvatar(@Validated UploadUserAvatarPO po) throws IOException {
        String avatarUrl = userService.uploadAvatar(po);
        return Resp.success(avatarUrl);
    }


    @NotLogin
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Resp<LoginUserVO> login(@RequestBody @Validated LoginUserPO po) {

        //判断当前用户是不是Admin用户
        if (!userService.isAdmin(po.getUsername())) {
            ExceptionUtil.business(500,"当前账号没有权限");
        }
        LoginUserVO loginUserVO = userService.userLogin(po);
        return Resp.success(loginUserVO);
    }

    @NotLogin
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Resp<Object> register(@RequestBody @Validated RegisterUserPO po) {
        userService.userRegister(po);
        return Resp.success();
    }

    @NotLogin
    @Operation(summary = "刷新Token")
    @GetMapping("/refresh")
    public Resp<TokenEntity> refresh(@NotEmpty(message = "refreshToken不能为空") String refreshToken) {
        TokenEntity tokenEntity = authService.refreshToken(refreshToken);
        return Resp.success(tokenEntity);
    }


    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/current")
    public Resp<UserVO> getCurrentUser() {
        UserVO vo = userService.getCurrentLoginUser(LoginIdUtil.getUserId());
        return Resp.success(vo);
    }

    @GetMapping("/logout")
    @Operation(summary = "用户登出")
    public Resp<Object> logout() {
        Long userId = LoginIdUtil.getUserId();
        authService.invalidToken(userId);
        return Resp.success();
    }

}
