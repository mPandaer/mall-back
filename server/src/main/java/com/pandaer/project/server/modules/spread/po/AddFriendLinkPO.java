package com.pandaer.project.server.modules.spread.po;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "添加友情链接的参数实体")
public class AddFriendLinkPO {


    @NotEmpty(message = "链接名字不能为空")
    @Schema(description = "链接名字")
    private String linkName;

    @NotEmpty(message = "链接的URL不能为空")
    @Schema(description = "链接的URL")
    private String linkUrl;

    @NotEmpty(message = "链接的图片URL不能为空")
    @Schema(description = "链接的图片URL")
    private String linkImgUrl;


}
