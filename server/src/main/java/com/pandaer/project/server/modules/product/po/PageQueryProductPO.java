package com.pandaer.project.server.modules.product.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "商品分页查询参数实体")
public class PageQueryProductPO {

    @Schema(description = "当前的页码")
    @NotNull(message = "页码不能为空")
    private Integer currentPage;

    @Schema(description = "每页的大小")
    private Integer pageSize = 10;

    @Schema(description = "商品名字")
    private String name;

    @Schema(description = "品牌ID")
    private Long brandId;

    @Schema(description = "类型ID")
    private Long typeId;

    @Schema(description = "颜色ID")
    private Long colorId;

    @Schema(description = "尺码ID")
    private Long sizeId;

}
