package com.pandaer.project.base.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FrameWorkException extends RuntimeException {
    private Integer code;
    private String message;

    public FrameWorkException(Integer code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
