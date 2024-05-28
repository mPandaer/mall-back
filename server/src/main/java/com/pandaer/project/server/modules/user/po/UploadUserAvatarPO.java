package com.pandaer.project.server.modules.user.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "上传头像的参数实体")
public class UploadUserAvatarPO {

    @Schema(description = "头像数据")
    @NotNull(message = "文件数据不能为空")
    MultipartFile file;

    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    Long userId;
}
