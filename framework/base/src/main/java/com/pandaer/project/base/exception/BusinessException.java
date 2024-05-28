package com.pandaer.project.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 整个项目的业务异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(Integer code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
