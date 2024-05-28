package com.pandaer.project.server.modules.product.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新颜色参数实体")
public class UpdateProductColorPO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "颜色唯一ID")
    private Long colorId;

    @Schema(description = "颜色名")
    private String colorName;

    @Schema(description = "颜色状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;
}
