package com.pandaer.project.server.modules.order.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.modules.product.vo.ProductVO;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Data
@Schema(description = "订单的详细信息")
public class OrderDetailVO {

    @Schema(description = "订单详细信息ID")
    @JsonSerialize(using = IdSerializer.class)
    private Long detailId;

    @Schema(description = "订单ID")
    @JsonSerialize(using = IdSerializer.class)
    private Long orderId;

    @Schema(description = "订单对应的商品信息")
    private ProductVO product;

    @Schema(description = "对应商品的下单数量")
    private Integer quantity;

}
