package com.pandaer.project.server.common.jwt;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * JWT工具,用于生成AccessToken以及RefreshToken 借助Hu-tool实现
 */

@Component
public class JwtProvider {
    @Resource
    private JwtProperties properties;

    /**
     * 签发时间key
     */
    public static final String IAT = "iat";

    /**
     * 过期时间key
     */
    public static final String EXP = "exp";


    /**
     * 暂时写死,后期扩展
     * 创建AccessToken
     * @param payload Token中要携带的数据
     * @return access令牌
     */
    public String createAccessToken(Map<String,Object> payload) {
        assembleDefaultKey(payload, properties.getAccessExpiration());
        return JWTUtil.createToken(payload,properties.getSecret().getBytes());
    }


    /**
     * 装配默认的payload信息
     * @param payload 用户自定义的payload信息
     */
    private void assembleDefaultKey(Map<String, Object> payload,Integer expiration) {
        if (!payload.containsKey(IAT)) {
            payload.put(IAT,DateTime.of(new Date()).toTimeStr());
        }
        if (!payload.containsKey(EXP)) {
            payload.put(EXP,DateUtil.offsetMillisecond(new Date(),expiration).toTimeStr());
        }
    }

    /**
     * 创建RefreshToken
     * @param payload 用户自定义的payload信息
     * @return refreshToken
     */
    public String createRefreshToken(Map<String, Object> payload) {
        assembleDefaultKey(payload,properties.getRefreshExpiration());
        return JWTUtil.createToken(payload,properties.getSecret().getBytes());
    }

    /**
     * 获取JWT中的值
     * @param token 要获取payload的Token
     * @param key payload中对应的key
     * @return key的值
     */
    public Object getPayloadBy(String token,String key) {
        JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
        return payloads.getObj(key);
    }

    /**
     * 验证Token的有效性
     * @param token 要验证的Token
     * @return 返回是否有效
     */
    public boolean validateToken(String token) {
        try {
            // 验证数据完整性
            boolean verify = JWTUtil.verify(token, properties.getSecret().getBytes());
            if (!verify) return false;
            //验证是否过期
            String exp = (String) getPayloadBy(token, EXP);
            DateTime dateTime = new DateTime(exp);
            return dateTime.isAfter(new Date());
        } catch (Exception e) {
            return false;
        }
    }






}
