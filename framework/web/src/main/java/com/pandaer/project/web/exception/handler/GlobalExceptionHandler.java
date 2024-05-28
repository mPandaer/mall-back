package com.pandaer.project.web.exception.handler;

import com.pandaer.project.base.exception.BusinessException;
import com.pandaer.project.base.exception.FrameWorkException;
import com.pandaer.project.web.reponse.Resp;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理器
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Resp<Object>> handleBusinessException(BusinessException exception) {
        log.error("业务异常: " + exception.getMessage());
        Resp<Object> error = Resp.error(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(error,getHttpStatus(exception.getCode()));
    }

    private HttpStatusCode getHttpStatus(Integer code) {
        return switch (code) {
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 403 -> HttpStatus.FORBIDDEN;
            // 添加其他的状态码处理
            default ->
                // 默认为 500 服务器内部错误
                    HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    /**
     * 框架异常处理器
     * @param exception
     * @return
     */
    @ExceptionHandler(FrameWorkException.class)
    public ResponseEntity<Resp<Object>> handleBusinessException(FrameWorkException exception) {
        log.error("框架异常: " + exception.getMessage());
        Resp<Object> error = Resp.error(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(error,getHttpStatus(exception.getCode()));
    }

    /**
     * 处理参数校验异常
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Resp<Object> handleModelValidException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("参数校验异常: " + msg);
        return Resp.error(400,msg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Resp<Object> handleArgValidException(ConstraintViolationException ex) {
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().stream().findFirst().orElse(null);
        if (constraintViolation == null) {
            return Resp.error();
        }
        String message = constraintViolation.getMessage();
        log.error("参数校验异常: " + message);
        return Resp.error(400,message);
    }


}
