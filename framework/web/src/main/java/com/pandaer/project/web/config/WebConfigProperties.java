package com.pandaer.project.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "project.web.config")
public class WebConfigProperties {
    private String corsMapping = "/**";
    private boolean allowedCredentials = true;
    private String[] allowedHeaders = {"*"};
    private String[] allowedMethods = {"*"};
    private String[] allowedOriginPatterns = {"*"};
}
