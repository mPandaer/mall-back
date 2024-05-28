package com.pandaer.project.server.modules.user.po;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 * 更新用户信息参数实体
 */
@Data
@Schema(description = "更新用户信息的参数实体")
public class UpdateUserPO {

    @Schema(description = "用户ID 不可修改")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户电子邮件")
    @Email(message = "电子邮件格式不正确")
    private String email;

    @Schema(description = "用户角色")
    private Integer role;

    @Schema(description = "头像链接")
    private String avatarUrl;


    @Schema(description = "用户状态  0--未封号 1--已封号")
    private Integer isEnable;
}
