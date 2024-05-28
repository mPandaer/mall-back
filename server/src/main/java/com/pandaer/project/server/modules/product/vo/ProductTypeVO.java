package com.pandaer.project.server.modules.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "类型VO实体")
public class ProductTypeVO {

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "类型Id")
    private Long typeId;

    @Schema(description = "类型名")
    private String typeName;

    @Schema(description = "类型状态 0--未入驻 1--入驻 默认入驻")
    private Integer isEnable;

    @Schema(description = "类型记录创建时间 即入驻时间")
    private LocalDateTime createTime;
}
