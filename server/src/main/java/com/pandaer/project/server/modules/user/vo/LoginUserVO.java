package com.pandaer.project.server.modules.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户登录的返回实体
 */
@Data
@Schema(description = "用户登录的响应实体")
public class LoginUserVO {
    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "登录的用户信息")
    private UserVO user;
}
