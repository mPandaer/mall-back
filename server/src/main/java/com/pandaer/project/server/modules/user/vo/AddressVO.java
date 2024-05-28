package com.pandaer.project.server.modules.user.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 地址响应实体
 */
@Schema(description = "地址响应实体")
@Data
public class AddressVO {

    @Schema(description = "地址唯一ID")
    @JsonSerialize(using = IdSerializer.class)
    private Long addressId;

    @Schema(description = "收货人姓名")
    private String recipientName;

    @Schema(description = "收货人电话")
    private String recipientPhone;

    @Schema(description = "收货人省份")
    private String province;

    @Schema(description = "收货人城市")
    private String city;

    @Schema(description = "收货人详细地址")
    private String address;
}
