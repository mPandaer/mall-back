package com.pandaer.project.server.modules.product.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新品牌参数实体")
public class UpdateProductBrandPO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "品牌唯一ID")
    private Long brandId;

    @Schema(description = "品牌名")
    private String brandName;

    @Schema(description = "品牌Logo")
    private String brandLogoUrl;

    @Schema(description = "品牌状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;
}
