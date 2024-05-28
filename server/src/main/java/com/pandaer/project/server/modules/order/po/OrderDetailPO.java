package com.pandaer.project.server.modules.order.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "订单明细参数实体")
public class OrderDetailPO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "商品ID")
    private Long productId;

    @Schema(description = "商品数量")
    private Integer quantity;

    @Schema(description = "商品单价")
    private BigDecimal unitPrice;

    @Schema(description = "商品总价")
    private BigDecimal totalPrice;


}
