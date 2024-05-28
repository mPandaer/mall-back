package com.pandaer.project.server.modules.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "增加地址参数实体")
@Data
public class AddAddressPO {

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    @JsonDeserialize(using = IdDeserializer.class)
    private Long userId;

    @NotEmpty(message = "收货人姓名不能为空")
    @Schema(description = "收货人姓名")
    private String recipientName;

    @NotEmpty(message = "收货人地址不能为空")
    @Schema(description = "收货人地址")
    private String recipientPhone;

    @NotEmpty(message = "地址省份不能为空")
    @Schema(description = "地址省份")
    private String province;

    @NotEmpty(message = "地址城市不能为空")
    @Schema(description = "地址城市")
    private String city;

    @NotEmpty(message = "地址详细不能为空")
    @Schema(description = "地址详细")
    private String address;

}
