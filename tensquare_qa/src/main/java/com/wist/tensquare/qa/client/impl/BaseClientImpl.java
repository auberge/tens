package com.wist.tensquare.qa.client.impl;

import com.wist.tensquare.qa.client.BaseClient;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.stereotype.Component;

@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public WebResult findById(String labelId) {
        return new WebResult(StatusCode.ERROR,false,"Base客户端出错");
    }
}
