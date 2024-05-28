package com.pandaer.project.server.modules.spread.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "分页查询链接的参数实体")
@Data
public class PageQueryFriendLinkPO {

    @Schema(description = "每页的大小")
    private Integer pageSize = 10;

    @NotNull(message = "页码不能为空")
    @Schema(description = "页码")
    private Integer currentPage;
}
