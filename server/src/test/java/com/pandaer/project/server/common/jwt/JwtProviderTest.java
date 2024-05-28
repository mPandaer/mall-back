package com.pandaer.project.server.common.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Resource
    private JwtProvider jwtProvider;

    @Test
    void createAccessToken() {
        Map<String,Object> map = MapUtil.<String,Object>builder().put("userId","99999").put(JwtProvider.EXP,100).build();
        String accessToken = jwtProvider.createAccessToken(map);
        System.out.println(accessToken);
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjEwMCwidXNlcklkIjoiOTk5OTkiLCJpYXQiOjE3MTU4NjY5MTF9.ukZ0qs2S49YwcGUiawENzmkAasKeLP0qYe3QTa3NbcM
    }

    @Test
    void createRefreshToken() {
    }

    @Test
    void getPayloadBy() {
    }

    @Test
    void validateToken() {
//        boolean res = jwtProvider.validateToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjEwMCwidXNlcklkIjoiOTk5OTkiLCJpYXQiOjE3MTU4NjY5MTF9.ukZ0qs2S49YwcGUiawENzmkAasKeLP0qYe3QTa3NbcM");
//        System.out.println(res);

    }
}