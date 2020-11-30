package com.wist.tensquare.base.controller;

import entity.WebResult;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public WebResult exception(Exception e) {
        return new WebResult(StatusCode.ERROR, false, e.getMessage());
    }
}
