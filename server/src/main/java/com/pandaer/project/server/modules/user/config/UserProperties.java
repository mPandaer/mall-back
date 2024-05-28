package com.pandaer.project.server.modules.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.user")
public class UserProperties {
    /**
     * 用户模块上传文件的保存目录
     */
    private String uploadDir;

    /**
     * 生成头像链接前缀
     */
    private String urlPrefix = "http://localhost:8080/uploads/";
}
