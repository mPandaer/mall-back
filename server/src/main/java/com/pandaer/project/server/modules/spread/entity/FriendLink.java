package com.pandaer.project.server.modules.spread.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 友情链接
 * @TableName friend_links
 */
@TableName(value ="friend_links")
@Data
public class FriendLink implements Serializable {
    /**
     * 链接ID
     */
    @TableId(value = "link_id")
    private Long linkId;

    /**
     * 链接名字
     */
    @TableField(value = "link_name")
    private String linkName;

    /**
     * 链接URL地址
     */
    @TableField(value = "link_url")
    private String linkUrl;

    /**
     * 链接图片地址
     */
    @TableField(value = "link_img_url")
    private String linkImgUrl;

    /**
     * 逻辑删除 0--未删除 1--删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建记录时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新记录的时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}