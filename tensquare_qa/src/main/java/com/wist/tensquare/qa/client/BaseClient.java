package com.wist.tensquare.qa.client;


import com.wist.tensquare.qa.client.impl.BaseClientImpl;
import entity.WebResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "tensquare-base", fallback = BaseClientImpl.class)
public interface BaseClient {
    @GetMapping(value = "/label/{labelId}")
    WebResult findById(@PathVariable(name = "labelId") String labelId);
}
