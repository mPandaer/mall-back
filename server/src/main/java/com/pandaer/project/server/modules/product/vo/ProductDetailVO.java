package com.pandaer.project.server.modules.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "商品详情数据响应实体")
public class ProductDetailVO {
    @Schema(description = "商品ID")
    @JsonSerialize(using = IdSerializer.class)
    private Long productId;

    @Schema(description = "商品名字")
    private String name;

    @Schema(description = "商品的图片链接列表")
    private String detailImgUrls;

    @Schema(description = "商品的价格")
    private BigDecimal price;


    @Schema(description = "商品的品牌")
    private ProductBrandVO brand;


    @Schema(description = "商品的类型")
    private ProductTypeVO type;


    @Schema(description = "商品的颜色")
    private List<ProductColorVO> colorList;

    @Schema(description = "商品的尺码")
    private List<ProductSizeVO> sizeList;

    @Schema(description = "商品的库存")
    private Integer inventory;

}
