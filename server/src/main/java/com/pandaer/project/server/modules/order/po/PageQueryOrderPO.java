package com.pandaer.project.server.modules.order.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "分页查询的参数实体")
@Data
public class PageQueryOrderPO {

    @Schema(description = "当前查询的页码")
    @NotNull(message = "查询的页码不能为空")
    private Integer currentPage;

    @Schema(description = "页的大小")
    private Integer pageSize = 10;

    @Schema(description = "订单的状态")
    private Integer orderStatus;
}
