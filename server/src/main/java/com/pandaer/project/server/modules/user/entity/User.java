package com.pandaer.project.server.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * 用户实体
 */
@TableName(value = "users")
@Data
public class User implements Serializable {
    /**
     * 用户的唯一标识符
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 用户名，唯一
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码哈希
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户电子邮件
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户角色 0 -- 普通用户  1 -- 管理员
     */
    @TableField(value = "role")
    private Integer role;

    /**
     * 头像URL
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 用户是否封号 0--未封号 1--已封号
     */
    @TableField(value = "is_enable")
    private Integer isEnable;

    /**
     * 用户的逻辑删除字段 0--未删除 1--已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建品牌的管理员ID
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 更新品牌的管理员ID
     */
    @TableField(value = "update_user")
    private Long updateUser;

    /**
     * 品牌创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 品牌更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}