package com.pandaer.project.server.modules.spread.po;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "添加广告参数实体")
@Data
public class AddADPO {

    @NotEmpty(message = "公司名不能为空")
    @Schema(description = "公司名")
    private String companyName;

    @NotNull(message = "广告费用不能为空")
    @Schema(description = "广告费用")
    private BigDecimal adCost;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull(message = "广告有效时间不能为空")
    @Schema(description = "广告有效时间")
    private LocalDateTime adValidTime;

    @NotEmpty(message = "广告链接不能为空")
    @Schema(description = "广告链接")
    private String adUrl;

    @NotEmpty(message = "广告图片不能为空")
    @Schema(description = "广告图片链接")
    private String adImgUrl;

}
