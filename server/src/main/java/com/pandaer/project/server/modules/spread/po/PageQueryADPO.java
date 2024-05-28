package com.pandaer.project.server.modules.spread.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "广告分页查询参数实体")
public class PageQueryADPO {

    @NotNull(message = "页码不能为空")
    @Schema(description = "当前的页码")
    private Integer currentPage;

    @Schema(description = "页的大小")
    private Integer pageSize = 10;
}
