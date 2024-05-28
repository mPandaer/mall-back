package com.pandaer.project.server.modules.spread.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.project.server.util.serialize.IdSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "友情链接响应实体")
@Data
public class FriendLinkVO {

    @JsonSerialize(using = IdSerializer.class)
    @Schema(description = "链接的唯一ID")
    private Long linkId;

    @Schema(description = "链接名字")
    private String linkName;

    @Schema(description = "链接的URL")
    private String linkUrl;

    @Schema(description = "链接的图片URL")
    private String linkImgUrl;
}
