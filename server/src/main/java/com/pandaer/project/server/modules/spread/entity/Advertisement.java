package com.pandaer.project.server.modules.spread.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName advertisements
 */
@TableName(value ="advertisements")
@Data
public class Advertisement implements Serializable {
    /**
     * 广告ID
     */
    @TableId(value = "ad_id")
    private Long adId;

    /**
     * 公司名字
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 广告费用
     */
    @TableField(value = "ad_cost")
    private BigDecimal adCost;

    /**
     * 广告有效时间
     */
    @TableField(value = "ad_valid_time")
    private LocalDateTime adValidTime;

    /**
     * 广告链接
     */
    @TableField(value = "ad_url")
    private String adUrl;

    /**
     * 广告图片
     */
    @TableField(value = "ad_img_url")
    private String adImgUrl;

    /**
     * 逻辑删除 0--未删除 1--已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}