package com.example.exception;

import com.example.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * 用于捕获全局异常并返回统一的错误响应
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        // 根据异常信息返回错误响应，如果没有异常信息，则返回默认的错误提示
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作异常");
    }
}

