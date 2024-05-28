package com.pandaer.project.server.common.cache;

/**
 * Cache服务
 */
public interface CacheService {

    /**
     * 让RefreshToken无效
     */
    void invalidRefreshToken(String key);

    /**
     * 保存RefreshToken
     * @param token
     * @param expiration
     */
    void saveRefreshToken(String key,String token,Integer expiration);

    String getRefreshToken(String key);
}
