package com.pandaer.project.server.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName product_brands
 */
@TableName(value ="product_brands")
@Data
public class ProductBrand implements Serializable {
    /**
     * 品牌唯一标识符
     */
    @TableId(value = "brand_id")
    private Long brandId;

    /**
     * 品牌名称
     */
    @TableField(value = "brand_name")
    private String brandName;

    /**
     * 品牌logo链接
     */
    @TableField(value = "brand_logo_url")
    private String brandLogoUrl;

    /**
     * 品牌是否入驻 0--未入驻 1--已入驻 
     */
    @TableField(value = "is_enable")
    private Integer isEnable;

    /**
     * 品牌的逻辑删除字段 0--未删除 1--已删除
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}