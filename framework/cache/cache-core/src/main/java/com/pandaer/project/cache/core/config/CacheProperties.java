package com.pandaer.project.cache.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Cache配置类
 */
@ConfigurationProperties(prefix = "project.cache.config")
@Data
public class CacheProperties {
    private String defaultCacheName = "PROJECT_DEFAULT_CACHE";
}
