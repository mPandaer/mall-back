package com.pandaer.project.server.common.file.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.base.exception.util.ExceptionUtil;
import com.pandaer.project.server.common.file.constants.FileConstant;
import com.pandaer.project.server.common.file.enums.SubjectEnum;
import com.pandaer.project.server.common.file.po.FileUploadPO;
import com.pandaer.project.server.common.file.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    /**
     * 保存文件到文件路径  todo 待优化,废弃
     * @param filePath
     * @param inputStream
     */
    @Override
    public void save(String filePath, InputStream inputStream) {
        try {
            Path path = Paths.get(filePath);
            // 保存文件到指定目录
            Files.copy(inputStream, path);
        } catch (IOException e) {
            throw new BusinessException(500,"文件保存失败");
        }
    }

    @Override
    public String upload(FileUploadPO po) {
        MultipartFile file = po.getFile();
        SubjectEnum subject = SubjectEnum.getSubjectByCode(po.getSubjectCode());

        if (ObjectUtil.isNull(file) || file.isEmpty()) {
            ExceptionUtil.business(500,"文件数据为空");
        }
        if (ObjectUtil.isNull(subject)) {
            ExceptionUtil.business(500,"上传主题无效");
        }

        // 生成唯一文件名
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        // 保存文件到指定目录
        Path filePath = Path.of(FileConstant.STORE_PATH_PREFIX, subject.getDir(), fileName);
        createAndCopyFile(filePath,file);
        // 生成文件访问 URL
        return URLUtil.completeUrl(FileConstant.URL_PREFIX,subject.getDir() + "/" + fileName);
    }

    private void createAndCopyFile(Path filePath,MultipartFile file) {
        try {
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            ExceptionUtil.business(500,"文件保存失败");
        }

    }
}
