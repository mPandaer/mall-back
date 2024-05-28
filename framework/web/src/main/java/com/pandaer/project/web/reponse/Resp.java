package com.pandaer.project.web.reponse;

import lombok.Data;

import java.io.Serializable;

/**
 * Web模块公用返回
 */

@Data
public class Resp<T> implements Serializable {
    private final Integer code;
    private final String message;
    private final T data;

    public Resp(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Resp<T> success(WebStatus status, T data) {
        return new Resp<>(status.code(), status.message(), data);
    }

    public static <T> Resp<T> success(T data) {
        return success(DefaultWebStatus.SuccessStatus, data);
    }

    public static  Resp<Object> success() {
        return success(DefaultWebStatus.SuccessStatus, null);
    }

    public static <T> Resp<T> error(WebStatus status) {
        return new Resp<>(status.code(),
                status.message(), null);
    }


    public static <T> Resp<T> error(String message) {
        return new Resp<>(DefaultWebStatus.ErrorStatus.code(),
                message, null);
    }

    public static <T> Resp<T> error(Integer code,String message) {
        return new Resp<>(code,
                message, null);
    }

    public static <T> Resp<T> error() {
        return new Resp<>(DefaultWebStatus.ErrorStatus.code(),
                DefaultWebStatus.ErrorStatus.message(), null);
    }


}
