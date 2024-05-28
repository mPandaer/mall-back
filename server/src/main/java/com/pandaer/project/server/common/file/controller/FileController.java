package com.pandaer.project.server.common.file.controller;

import com.pandaer.project.server.common.file.po.FileUploadPO;
import com.pandaer.project.server.common.file.service.FileService;
import com.pandaer.project.web.reponse.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "文件上传服务",description = "提供图片上传的功能")
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public Resp<String> fileUpload(@Validated FileUploadPO po) {
        String url = fileService.upload(po);
        return Resp.success(url);
    }

}
