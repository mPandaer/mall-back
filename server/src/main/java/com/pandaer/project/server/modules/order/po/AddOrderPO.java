package com.pandaer.project.server.modules.order.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "下订单参数实体")
@Data
public class AddOrderPO {

    @NotNull(message = "用户ID不能为空")
    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "用户ID")
    private Long userId;

    @NotNull(message = "地址ID不能为空")
    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "地址ID")
    private Long addressId;

    @NotNull(message = "应付金额不能为空")
    @Schema(description = "应付金额")
    private BigDecimal totalAmount;

    @NotNull(message = "订单明细不能为空")
    @Schema(description = "订单明细")
    private List<OrderDetailPO> details;
}
