package com.pandaer.project.server.modules.spread.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pandaer.project.server.util.deserialize.IdDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "更新友情链接的参数实体")
@Data
public class UpdateFriendLinkPO {

    @JsonDeserialize(using = IdDeserializer.class)
    @Schema(description = "链接的唯一ID")
    private Long linkId;

    @Schema(description = "链接名字")
    private String linkName;

    @Schema(description = "链接的URL")
    private String linkUrl;


    @Schema(description = "链接的图片URL")
    private String linkImgUrl;

}
