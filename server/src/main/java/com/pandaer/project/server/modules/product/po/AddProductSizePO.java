package com.pandaer.project.server.modules.product.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 添加产品尺码的的参数实体
 */
@Schema(description = "添加产品尺码的参数实体")
@Data
public class AddProductSizePO {

    @Schema(description = "尺码名")
    @NotEmpty(message = "尺码名不能为空")
    private String sizeName;

    @Schema(description = "尺码状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

}
