package com.pandaer.project.server.util;

import cn.hutool.core.util.ObjectUtil;
import com.pandaer.project.base.exception.BusinessException;

public class LoginIdUtil {
    private static final ThreadLocal<Long> idCache = new ThreadLocal<>();

    public static Long getUserId() {
        Long userId = idCache.get();
        if (ObjectUtil.isNull(userId)) {
            throw new BusinessException(500,"上流并没有UserId");
        }
        return userId;
    }

    public static void setUserId(Long userId) {
        idCache.set(userId);
    }


}
