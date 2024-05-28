package com.pandaer.project.server.modules.product.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新类型参数实体")
public class UpdateProductTypePO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "类型唯一ID")
    private Long typeId;

    @Schema(description = "类型名")
    private String typeName;

    @Schema(description = "类型状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;
}
