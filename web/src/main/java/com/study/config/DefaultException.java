package com.study.config;

import com.study.common.CommonException;
import com.study.common.ResultJson;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultException {
    @ExceptionHandler
    ResultJson error(Exception e) {
        e.printStackTrace();
        if (e instanceof CommonException) {
            return ResultJson.failed(e.getMessage());
        }
        return ResultJson.failed("系统异常，请联系管理员");
    }
}
