package com.pandaer.project.server.modules.user.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "用户登录参数实体")
public class LoginUserPO {

    @Schema(description = "登录名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码的长度必须在6~16位之间")
    @Schema(description = "登录密码")
    private String password;
}
