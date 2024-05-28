package com.pandaer.project.cache.core.config;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class CacheConfig {

    @Resource
    private CacheProperties cacheProperties;

    /**
     * 配置一个默认的缓存
     * @param cacheManager
     * @return
     */
    @ConditionalOnBean(CacheManager.class)
    @Bean
    public Cache defaultCache(CacheManager cacheManager) {
        Cache cache = cacheManager.getCache(cacheProperties.getDefaultCacheName());
        if (ObjectUtil.isNull(cache)) {
            throw new RuntimeException("初始化默认缓存异常");
        }
        return cache;
    }
}
