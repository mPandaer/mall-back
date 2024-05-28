package com.pandaer.project.server.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName order_status_history
 */
@TableName(value ="order_status_history")
@Data
public class OrderStatusHistory implements Serializable {
    /**
     * 状态变更记录ID
     */
    @TableId(value = "history_id", type = IdType.AUTO)
    private Long historyId;

    /**
     * 订单ID，关联订单表的逻辑外键
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 原订单状态
     */
    @TableField(value = "original_status")
    private Integer originalStatus;

    /**
     * 新订单状态
     */
    @TableField(value = "new_status")
    private Integer newStatus;

    /**
     * 状态变更时间
     */
    @TableField(value = "change_time")
    private LocalDateTime changeTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}