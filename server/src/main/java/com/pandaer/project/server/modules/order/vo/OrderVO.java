package com.pandaer.project.server.modules.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.modules.user.vo.AddressVO;
import com.pandaer.project.server.modules.user.vo.UserVO;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单基本数据的响应实体
 */
@Schema(description = "订单基本数据的响应实体")
@Data
public class OrderVO {
    /**
     * 订单ID
     */

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "订单ID")
    private Long orderId;

    /**
     * 0 -- 待发货
     * 1 -- 运输中
     * 2 -- 交易完成
     * 3 -- 申请退货
     * 4 -- 已退货
     * 5 -- 已取消
     */
    @Schema(description = "订单的状态")
    private Integer orderStatus;

    /**
     * 订单的总金额
     */
    @Schema(description = "订单的总金额")
    private BigDecimal totalAmount;

    /**
     * 下单时间
     */
    @Schema(description = "订单的创建时间")
    private LocalDateTime createTime;


    /**
     * 购买订单的用户基本信息
     */
    @Schema(description = "订单用户的基本信息")
    private UserVO user;


    /**
     * 订单的地址基本信息
     */
    @Schema(description = "订单地址的基本信息")
    private AddressVO address;

}
