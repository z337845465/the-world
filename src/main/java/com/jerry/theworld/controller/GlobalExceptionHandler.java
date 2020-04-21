package com.jerry.theworld.controller;

import com.alibaba.fastjson.JSON;
import com.jerry.theworld.controller.respone.ResponseResult;
import com.jerry.theworld.tools.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author:Jerry
 * 2020/4/15
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult fail(Exception e){
        log.error(ExceptionUtil.printExceptionInfo(e));
        return ResponseResult.fail("请求错误");
    }
}
