package com.pandaer.project.server.modules.product.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 添加产品类型的的参数实体
 */
@Schema(description = "添加产品类型的参数实体")
@Data
public class AddProductTypePO {

    @Schema(description = "类型名")
    @NotEmpty(message = "类型名不能为空")
    private String typeName;

    @Schema(description = "类型状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

}
