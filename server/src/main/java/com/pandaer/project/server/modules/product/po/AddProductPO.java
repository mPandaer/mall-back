package com.pandaer.project.server.modules.product.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(description = "添加产品参数实体")
public class AddProductPO {

    @NotEmpty(message = "商品名字不能为空")
    @Schema(description = "商品的名字")
    private String name;

    @Min(value = 0,message = "商品的价格不能小于0")
    @Max(value = 99999999,message = "商品的价格不能超过99999999")
    @Schema(description = "商品的价格")
    private BigDecimal price;

    @NotNull(message = "商品库存不能为空")
    @Schema(description = "商品库存")
    @Min(value = 0,message = "商品的库存不能小于0")
    @Max(value = 99999999,message = "商品的库存不能超过99999999")
    private Integer inventory;

    @NotNull(message = "商品品牌对应的ID不能为空")
    @Schema(description = "商品品牌对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long brandId;

    @NotNull(message = "商品类型对应的ID不能为空")
    @Schema(description = "商品类型对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long typeId;

    @NotNull(message = "商品颜色对应的ID不能为空")
    @Schema(description = "商品颜色对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long colorId;

    @NotNull(message = "商品尺码对应的ID不能为空")
    @Schema(description = "商品尺码对应的ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long sizeId;

    @Schema(description = "商品的详情图片URLs")
    private String detailImgUrls;

    @Schema(description = "商品是否上架 0--未上架 1--已上架")
    private Integer isEnable;



}
