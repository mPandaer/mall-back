package com.pandaer.project.server.modules.spread.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import com.pandaer.project.server.util.serialize.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "广告响应实体")
public class ADVO {

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "广告ID")
    private Long adId;


    @Schema(description = "公司名")
    private String companyName;

    @Schema(description = "广告费用")
    private BigDecimal adCost;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Schema(description = "广告有效时间")
    private LocalDateTime adValidTime;

    @Schema(description = "广告链接")
    private String adUrl;

    @Schema(description = "广告图片链接")
    private String adImgUrl;
}
