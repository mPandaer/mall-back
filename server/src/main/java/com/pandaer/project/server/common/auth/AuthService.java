package com.pandaer.project.server.common.auth;

import com.pandaer.project.server.common.auth.entity.TokenEntity;

import java.util.HashMap;
import java.util.Map;

public interface AuthService {
    // todo 待优化
    TokenEntity genToken(Map<String,Object> payload);

    //TODO 待优化
    TokenEntity refreshToken(String refreshToken);

    void invalidToken(Long userId);
}
