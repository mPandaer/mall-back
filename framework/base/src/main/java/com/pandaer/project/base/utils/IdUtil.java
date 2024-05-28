package com.pandaer.project.base.utils;

public class IdUtil {

    public static Long genNextId() {
        return cn.hutool.core.util.IdUtil.getSnowflakeNextId();
    }
}
