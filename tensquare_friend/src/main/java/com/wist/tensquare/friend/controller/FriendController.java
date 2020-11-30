package com.wist.tensquare.friend.controller;

import com.wist.tensquare.friend.client.UserClient;
import com.wist.tensquare.friend.service.FriendService;
import entity.StatusCode;
import entity.WebResult;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author simptx
 */
@RestController
@RequestMapping(value = "/friend")
public class FriendController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private FriendService friendService;
    @Resource
    private UserClient userClient;

    /**
     * 添加好友或添加非好友
     *
     * @return
     */
    @PostMapping(value = "/like/{friendId}/{type}")
    public WebResult addFriend(@PathVariable String friendId, @PathVariable String type) {
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null || "".equals(claims)) {
            return new WebResult(StatusCode.ACCESSERROR, false, "权限不足");
        }
        String userId = claims.getId();
        if (type != null) {
            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(userId, friendId, type);
                if (flag == 0) {
                    return new WebResult(StatusCode.ERROR, false, "不能重复添加好友");
                } else if (flag == 1) {
                    userClient.updatefanscountandfollowcount(userId,friendId,1);
                    return new WebResult(StatusCode.OK, true, "添加成功");
                }
            } else if ("2".equals(type)) {
                int flag = friendService.addNofriend(userId, friendId);
                if (flag == 0) {
                    return new WebResult(StatusCode.ERROR, false, "不能重复添加非好友");
                } else if (flag == 1) {
                    return new WebResult(StatusCode.OK, true, "添加成功");
                }
            }
            return new WebResult(StatusCode.ERROR, false, "参数异常");
        } else {
            return new WebResult(StatusCode.ERROR, false, "参数异常");
        }
    }

    @DeleteMapping(value = "/{friendId}")
    public WebResult deleteFriend(@PathVariable String friendId){
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null || "".equals(claims)) {
            return new WebResult(StatusCode.ACCESSERROR, false, "权限不足");
        }
        String userId = claims.getId();
        friendService.deleteFriend(userId,friendId);
        userClient.updatefanscountandfollowcount(userId,friendId,-1);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

}
