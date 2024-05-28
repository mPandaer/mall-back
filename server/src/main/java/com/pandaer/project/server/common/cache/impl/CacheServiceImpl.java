package com.pandaer.project.server.common.cache.impl;

import com.pandaer.project.server.common.cache.CacheService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {

    private final String TOKEN_PREFIX = "LOGIN:REFRESH_TOKEN_";

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 让Redis中的RefreshToken无效
     */
    @Override
    public void invalidRefreshToken(String key) {
        redisTemplate.delete(TOKEN_PREFIX + key);
    }


    /**
     * 保存 RefreshToken
     * 1. 用RedisTemplate保存 可以设置过期时间
     * @param token
     * @param expiration
     */
    @Override
    public void saveRefreshToken(String key,String token, Integer expiration) {
        redisTemplate.opsForValue().set(TOKEN_PREFIX + key,token,expiration, TimeUnit.MILLISECONDS);
    }

    @Override
    public String getRefreshToken(String key) {
        return (String) redisTemplate.opsForValue().get(TOKEN_PREFIX + key);
    }
}
