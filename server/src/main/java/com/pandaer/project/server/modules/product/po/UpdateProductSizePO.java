package com.pandaer.project.server.modules.product.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新尺码参数实体")
public class UpdateProductSizePO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "尺码唯一ID")
    private Long sizeId;

    @Schema(description = "尺码名")
    private String sizeName;

    @Schema(description = "尺码状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;
}
