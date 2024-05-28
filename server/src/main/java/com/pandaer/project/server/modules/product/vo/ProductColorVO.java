package com.pandaer.project.server.modules.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "颜色VO实体")
public class ProductColorVO {

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "颜色Id")
    private Long colorId;
    @Schema(description = "颜色名")
    private String colorName;

    @Schema(description = "颜色状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

    @Schema(description = "颜色记录创建时间 即入驻时间")
    private LocalDateTime createTime;
}
