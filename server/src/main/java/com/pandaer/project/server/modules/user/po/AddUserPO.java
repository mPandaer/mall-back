package com.pandaer.project.server.modules.user.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Schema(description = "添加用户参数实体")
@Data
public class AddUserPO {
    /**
     * 用户名，唯一
     */
    @NotEmpty(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "用户名不能为空")
    @Schema(description = "密码")
    private String password;

    /**
     * 用户电子邮件
     */
    @Email(message = "必须符合邮箱格式")
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像链接")
    private String avatarUrl;


}
