package com.wist.tensquare.gathering.web.common;

import entity.WebResult;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller的公共异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {

//默认抓取所有类型异常
    @ExceptionHandler
    public WebResult error(Throwable e){
              e.printStackTrace();
        //返回响应结果
        return  new WebResult(StatusCode.ERROR,false, e.getMessage());
    }
}
