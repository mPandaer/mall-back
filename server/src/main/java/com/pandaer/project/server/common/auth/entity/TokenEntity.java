package com.pandaer.project.server.common.auth.entity;

import lombok.Data;

/**
 * Token实体
 */
@Data
public class TokenEntity {
    private String accessToken;
    private String refreshToken;
}
