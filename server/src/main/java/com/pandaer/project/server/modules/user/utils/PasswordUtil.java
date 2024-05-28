package com.pandaer.project.server.modules.user.utils;

import cn.hutool.crypto.digest.BCrypt;

/**
 * 负责处理密码的hash与验证
 */
public class PasswordUtil {

    public static String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword);
    }

    public static boolean verifyPassword(String hashPassword,String rawPassword) {
        return BCrypt.checkpw(rawPassword,hashPassword);
    }

}
