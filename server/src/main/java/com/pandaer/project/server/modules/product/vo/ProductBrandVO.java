package com.pandaer.project.server.modules.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "产品VO实体")
public class ProductBrandVO {

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "品牌Id")
    private Long brandId;

    @Schema(description = "品牌名")
    private String brandName;

    @Schema(description = "品牌状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

    @Schema(description = "品牌记录创建时间 即入驻时间")
    private LocalDateTime createTime;

    @Schema(description = "品牌的Logo")
    private String brandLogoUrl;
}
