package com.pandaer.project.server.modules.product.po;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 添加产品品牌的的参数实体
 */
@Schema(description = "添加产品品牌的参数实体")
@Data
public class AddProductBrandPO {

    @Schema(description = "品牌名")
    @NotEmpty(message = "品牌名不能为空")
    private String brandName;

    @Schema(description = "品牌状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

    @Schema(description = "品牌Logo")
    private String brandLogoUrl;

}
