package com.pandaer.project.server.modules.user.controller;

import com.pandaer.project.server.aspect.annotation.NotLogin;
import com.pandaer.project.server.common.auth.AuthService;
import com.pandaer.project.server.common.auth.entity.TokenEntity;
import com.pandaer.project.server.modules.user.po.*;
import com.pandaer.project.server.modules.user.service.AddressService;
import com.pandaer.project.server.modules.user.service.UserService;
import com.pandaer.project.server.modules.user.vo.AddressVO;
import com.pandaer.project.server.modules.user.vo.LoginUserVO;
import com.pandaer.project.server.modules.user.vo.UserVO;
import com.pandaer.project.server.util.LoginIdUtil;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@Tag(name = "公开的用户服务", description = "提供登录注册的功能")
public class ClientUserController {

    @Resource
    private UserService userService;

    @Resource
    private AuthService authService;

    @Resource
    private AddressService addressService;

    @NotLogin
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Resp<LoginUserVO> login(@RequestBody @Validated LoginUserPO po) {
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

    @PostMapping("/address/add")
    @Operation(summary = "添加地址")
    public Resp<Object> addAddress(@RequestBody @Validated AddAddressPO po) {
        String addressId = addressService.addAddress(po);
        return Resp.success(addressId);
    }

    @PostMapping("/address/update")
    @Operation(summary = "修改地址")
    public Resp<Object> updateAddress(@RequestBody @Validated UpdateAddressPO po) {
        addressService.updateAddress(po);
        return Resp.success();
    }

    @GetMapping("/address/delete")
    @Operation(summary = "删除地址")
    public Resp<Object> deleteAddress(@Validated DeleteAddressPO po) {
        addressService.deleteAddress(po);
        return Resp.success();
    }

    @GetMapping("/address/query")
    @Operation(summary = "查询用户地址")
    public Resp<List<AddressVO>> queryAddressByUserId(@NotEmpty(message = "用户ID不能为空") String userId) {
        List<AddressVO> list = addressService.queryAllAddress(userId);
        return Resp.success(list);
    }

    @PostMapping("/password/update")
    @Operation(summary = "更新用户密码")
    public Resp<Object> queryAddressByUserId(@RequestBody @Validated UpdatePasswordPO po) {
        userService.updatePassword(po);
        return Resp.success();
    }

    @Operation(summary = "修改用户")
    @PostMapping("/update")
    public Resp<Object> updateUser(@Validated @RequestBody UpdateUserPO po) {
        userService.updateUser(po);
        return Resp.success();
    }



}
