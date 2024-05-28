package com.pandaer.project.server.modules.product.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 添加产品颜色的的参数实体
 */
@Schema(description = "添加产品颜色的参数实体")
@Data
public class AddProductColorPO {

    @Schema(description = "颜色名")
    @NotEmpty(message = "颜色名不能为空")
    private String colorName;

    @Schema(description = "颜色状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

}
