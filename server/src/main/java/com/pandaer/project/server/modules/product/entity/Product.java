package com.pandaer.project.server.modules.product.entity;

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
 * @TableName products
 */
@TableName(value ="products")
@Data
public class Product implements Serializable {
    /**
     * 商品唯一标识符
     */
    @TableId(value = "product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 商品详情图片
     */
    @TableField(value = "detail_img_urls")
    private String detailImgUrls;

    /**
     * 商品的价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 商品的品牌 关联品牌表的逻辑外键
     */
    @TableField(value = "brand_id")
    private Long brandId;

    /**
     * 商品的类型 关联类型表的逻辑外键
     */
    @TableField(value = "type_id")
    private Long typeId;

    /**
     * 颜色对应的ID
     */
    @TableField(value = "color_id")
    private Long colorId;

    /**
     * 尺码对应的ID
     */
    @TableField(value = "size_id")
    private Long sizeId;

    /**
     * 商品的库存 关联库存表的逻辑外键
     */
    @TableField(value = "inventory")
    private Integer inventory;

    /**
     * 商品是否上架 0--未上架 1--已上架
     */
    @TableField(value = "is_enable")
    private Integer isEnable;

    /**
     * 商品的逻辑删除字段 0--未删除 1--已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /**
     * 创建商品的管理员ID
     */
    @TableField(value = "create_user")
    private Long createUser;

    /**
     * 更新商品的管理员ID
     */
    @TableField(value = "update_user")
    private Long updateUser;

    /**
     * 商品创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 商品更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}