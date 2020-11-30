package com.wist.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {
    @PutMapping(value = "/user/{userId}/{friendId}/{x}")
    void updatefanscountandfollowcount(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId, @PathVariable("x") int x);
}
