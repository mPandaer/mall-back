package com.pandaer.project.server.common.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.auth.jwt")
public class JwtProperties {

    /**
     * JWT的加密秘钥
     * 默认的随机32位字符串
     */
    private String secret = "KzeVaY8LkiUi6IMnKTvBrGxZDJ51Vh3Z";

    /**
     * AccessToken的过期时间
     * 默认 10min
     */
    private Integer accessExpiration = 10 * 60 * 1000;

    /**
     * RefreshToken的过期时间
     * 默认 5day
     */
    private Integer refreshExpiration = 5 * 24 * 60 * 60 * 1000;

}
