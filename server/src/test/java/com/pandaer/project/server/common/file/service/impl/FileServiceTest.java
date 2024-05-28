package com.pandaer.project.server.common.file.service.impl;
import com.pandaer.project.server.common.file.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.pandaer.project.server.common.file.po.FileUploadPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Resource
    private FileService fileService;

    @Test
    void upload() {
        FileUploadPO fileUploadPO = new FileUploadPO();
        fileUploadPO.setFile(new MockMultipartFile("test.txt","hello,file upload".getBytes()));
        fileUploadPO.setSubjectCode(0);
        String upload = fileService.upload(fileUploadPO);
        System.out.println(upload);
    }
}