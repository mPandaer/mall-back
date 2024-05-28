package com.pandaer.project.server.modules.user.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "更新密码参数实体")
public class UpdatePasswordPO {

    @NotEmpty(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;

    @NotEmpty(message = "重复新密码不能为空")
    @Schema(description = "重复的新密码")
    private String confirmPassword;
}
