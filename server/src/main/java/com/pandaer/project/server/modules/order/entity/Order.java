package com.pandaer.project.server.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Order implements Serializable {
    /**
     * 订单ID
     */
    @TableId(value = "order_id")
    private Long orderId;

    /**
     * 关联用户表的逻辑外键
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 关联送货地址表的逻辑外键
     */
    @TableField(value = "address_id")
    private Long addressId;

    /**
     * 0 -- 待发货
1 -- 运输中
2 -- 交易完成
3 -- 申请退款
4 -- 已退货
5 -- 已取消
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 订单的总金额
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 品牌的逻辑删除字段 0--未删除 1--已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建订单的管理员ID
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 更新订单的管理员ID
     */
    @TableField(value = "update_user")
    private Long updateUser;

    /**
     * 订单创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 订单更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}