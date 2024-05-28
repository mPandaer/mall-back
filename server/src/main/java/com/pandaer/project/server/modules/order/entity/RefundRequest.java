package com.pandaer.project.server.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName refund_requests
 */
@TableName(value ="refund_requests")
@Data
public class RefundRequest implements Serializable {
    /**
     * 退款申请唯一标识符
     */
    @TableId(value = "request_id", type = IdType.AUTO)
    private Long requestId;

    /**
     * 关联订单表的逻辑外键
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 退款金额
     */
    @TableField(value = "refund_amount")
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    @TableField(value = "refund_reason")
    private String refundReason;

    /**
     * 处理状态，0--待处理 1--已处理
     */
    @TableField(value = "processing_status")
    private Integer processingStatus;

    /**
     * 申请退款时间
     */
    @TableField(value = "request_time")
    private LocalDateTime requestTime;

    /**
     * 处理退款申请的管理员ID
     */
    @TableField(value = "process_user")
    private Long processUser;

    /**
     * 处理退款申请的时间
     */
    @TableField(value = "process_time")
    private LocalDateTime processTime;

    /**
     * 退款申请创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 退款申请更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}