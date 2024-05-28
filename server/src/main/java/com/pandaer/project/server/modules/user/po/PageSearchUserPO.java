package com.pandaer.project.server.modules.user.po;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "分页查询用户信息参数实体")
public class PageSearchUserPO {

    @Schema(description = "每页的大小",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private long pageSize = 10;

    @Schema(description = "当前的页码")
    @NotNull(message = "当前页码不能为空")
    private long currentPage;

    @Schema(description = "搜索的名字")
    private String name;

    @Schema(description = "搜索的邮箱")
    private String email;

    @Schema(description = "搜索的用户角色")
    private Integer role;

    @Schema(description = "搜索的用户状态")
    private Integer isEnable;

}
