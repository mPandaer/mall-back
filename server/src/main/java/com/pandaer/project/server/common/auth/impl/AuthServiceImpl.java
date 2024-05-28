package com.pandaer.project.server.common.auth.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.server.common.cache.CacheService;
import com.pandaer.project.server.common.jwt.JwtProvider;
import com.pandaer.project.server.common.auth.AuthService;
import com.pandaer.project.server.common.auth.entity.TokenEntity;
import jakarta.annotation.Resource;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * 公共的认证服务
 * 提供 Token的生成 Token的删除
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private JwtProvider jwtProvider;

    @Resource
    private CacheService cacheService;



    @Override
    public TokenEntity genToken(Map<String,Object> payload) {
        String accessToken = jwtProvider.createAccessToken(payload);
        String refreshToken = jwtProvider.createRefreshToken(payload);
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setAccessToken(accessToken);
        tokenEntity.setRefreshToken(refreshToken);
        return tokenEntity;
    }

    /**
     * 刷新AccessToken
     * 1. 校验RefreshToken的有效性
     * 2. 检查RefreshToken是否在Redis中存在
     * 3. 重新生成AccessToken
     * @param refreshToken
     * @return
     */
    @Override
    public TokenEntity refreshToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new BusinessException(401,"RefreshToken失效");
        }

        if (!isRefreshTokenInCache(refreshToken)) {
            throw new BusinessException(401,"RefreshToken失效");
        }
        try {
            Long userId = (Long) jwtProvider.getPayloadBy(refreshToken, "userId");
            Map<String,Object> map = MapUtil.<String,Object>builder().put("userId",userId).build();
            String accessToken = jwtProvider.createAccessToken(map);
            //处理返回对象
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setRefreshToken(refreshToken);
            tokenEntity.setAccessToken(accessToken);
            return tokenEntity;
        } catch (Exception e) {
            throw new BusinessException(500,"服务器内部错误: refreshToken");
        }


    }

    /**
     * 让RefreshToken无效
     * @param userId
     */
    @Override
    public void invalidToken(Long userId) {
        cacheService.invalidRefreshToken(String.valueOf(userId));
    }

    /**
     * 判断refreshToken是不是在Cache中,以及与Cache的是否相等
     * @param refreshToken
     * @return
     */
    private boolean isRefreshTokenInCache(String refreshToken) {
        try {
            Long userId = (Long) jwtProvider.getPayloadBy(refreshToken, "userId");
            String cacheToken = cacheService.getRefreshToken(String.valueOf(userId));
            return StrUtil.equals(cacheToken, refreshToken);
        } catch (Exception e) {
            return false;
        }
    }
}
