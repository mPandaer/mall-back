package com.pandaer.project.server.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName addresses
 */
@TableName(value ="addresses")
@Data
public class Address implements Serializable {
    /**
     * 地址的唯一标识符
     */
    @TableId(value = "address_id")
    private Long addressId;

    /**
     * 关联用户表的逻辑外键
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 收件人姓名
     */
    @TableField(value = "recipient_name")
    private String recipientName;

    /**
     * 收件人电话

     */
    @TableField(value = "recipient_phone")
    private String recipientPhone;

    /**
     * 省份
     */
    @TableField(value = "province")
    private String province;

    /**
     * 城市

     */
    @TableField(value = "city")
    private String city;

    /**
     * 详细地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 逻辑删除字段 0--未删除 1--已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建人ID
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 更新人ID
     */
    @TableField(value = "update_user")
    private Long updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}