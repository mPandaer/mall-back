package com.pandaer.project.base.exception.util;

import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.base.exception.FrameWorkException;

public class ExceptionUtil {

    public static void business(Integer code,String message) {
        throw new BusinessException(code, message);
    }

    public static void framework(Integer code,String message) {
        throw new FrameWorkException(code, message);
    }
}
