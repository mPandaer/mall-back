package com.pandaer.project.server.common.file.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "文件上传参数实体")
@Data
public class FileUploadPO {

    @Schema(description = "文件数据")
    private MultipartFile file;

    @NotNull(message = "不能为空")
    @Schema(description = "上传的主题的编码,比如商品详细图片,用户头像")
    private Integer subjectCode;
}
