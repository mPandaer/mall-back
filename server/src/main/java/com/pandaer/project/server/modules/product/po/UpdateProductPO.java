package com.pandaer.project.server.modules.product.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "更新产品信息参数实体")
public class UpdateProductPO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "商品ID")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "商品的名字")
    private String name;

    @Schema(description = "商品的价格")
    private BigDecimal price;

    @Schema(description = "商品库存")
    private Integer inventory;

    @Schema(description = "商品品牌对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long brandId;

    @Schema(description = "商品类型对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long typeId;


    @Schema(description = "商品颜色对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long colorId;


    @Schema(description = "商品尺码对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long sizeId;

    @Schema(description = "商品图片URLs")
    private String detailImgUrls;


    @Schema(description = "商品是否上架 0--未上架 1--已上架")
    private Integer isEnable;
}
