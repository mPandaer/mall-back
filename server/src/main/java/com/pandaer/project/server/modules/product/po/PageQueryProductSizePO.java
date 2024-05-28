package com.pandaer.project.server.modules.product.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "分页查询的参数实体")
public class PageQueryProductSizePO {

    @Schema(description = "每页的大小",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private long pageSize = 10;

    @Schema(description = "当前的页码")
    @NotNull(message = "当前页码不能为空")
    private long currentPage;
}
