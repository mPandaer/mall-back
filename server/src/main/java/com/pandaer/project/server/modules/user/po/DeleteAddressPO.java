package com.pandaer.project.server.modules.user.po;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "删除地址参数实体")
@Data
public class DeleteAddressPO {
    @NotNull(message = "地址ID不能为空")
    @Schema(description = "地址ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long addressId;

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long userId;
}
