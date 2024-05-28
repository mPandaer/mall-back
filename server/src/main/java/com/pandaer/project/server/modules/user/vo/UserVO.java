package com.pandaer.project.server.modules.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息响应实体")
public class UserVO {

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户姓名")
    private String username;

    @Schema(description = "用户邮箱地址")
    private String email;

    @Schema(description = "用户的角色 0--普通用户  1--管理员用户")
    private Integer role;

    @Schema(description = "用户头像地址")
    private String avatarUrl;

    @Schema(description = "用户是否封号 0--未封号 1--封号")
    private Integer isEnable;
}
