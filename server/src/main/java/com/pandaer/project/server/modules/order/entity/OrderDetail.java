package com.pandaer.project.server.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName order_details
 */
@TableName(value ="order_details")
@Data
public class OrderDetail implements Serializable {
    /**
     * 订单详情ID
     */
    @TableId(value = "detail_id", type = IdType.AUTO)
    private Long detailId;

    /**
     * 订单ID，关联订单表的逻辑外键
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 商品ID，关联商品表的逻辑外键
     */
    @TableField(value = "product_id")
    private Long productId;

    /**
     * 商品数量
     */
    @TableField(value = "quantity")
    private Integer quantity;

    /**
     * 商品单价
     */
    @TableField(value = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 商品总价
     */
    @TableField(value = "total_price")
    private BigDecimal totalPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}