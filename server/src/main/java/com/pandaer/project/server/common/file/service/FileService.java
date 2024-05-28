package com.pandaer.project.server.common.file.service;

import com.pandaer.project.server.common.file.po.FileUploadPO;

import java.io.InputStream;

/**
 * 专门所有后端模块的文件处理逻辑
 */
public interface FileService {

    void save(String filePath, InputStream inputStream);

    String upload(FileUploadPO po);
}
