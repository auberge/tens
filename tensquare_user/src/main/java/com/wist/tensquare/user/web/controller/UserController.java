package com.wist.tensquare.user.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.wist.tensquare.user.pojo.User;
import com.wist.tensquare.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import entity.PageResult;
import entity.WebResult;
import entity.StatusCode;
import util.JwtUtil;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author simptx
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 更新本人关注数个关注用户粉丝数
     */
    @PutMapping(value = "/{userId}/{friendId}/{x}")
    public void updatefanscountandfollowcount(@PathVariable String userId,@PathVariable String friendId,@PathVariable int x){
        userService.updatefanscountandfollowcount(userId,friendId,x);
    }

    @PostMapping(value = "/login")
    public WebResult login(@RequestBody User user){
        user=userService.login(user.getMobile(),user.getPassword());
        if (user==null){
            return new WebResult(StatusCode.LOGINERROR, false, "登陆失败");
        }
        String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("roles","user");
        map.put("name",user.getNickname());
        return new WebResult(StatusCode.OK, true, "登录成功",map);
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @PostMapping(value = "/sendsms/{mobile}")
    public WebResult sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new WebResult(StatusCode.OK, true, "发送成功");
    }

    @PostMapping(value = "/register/{code}")
    public WebResult register(@PathVariable String code, @RequestBody User user) {
        String redisCheckCode = (String)redisTemplate.opsForValue().get("checkCode" + user.getMobile());
        if (!Objects.equals(redisCheckCode, code) || "".equals(redisCheckCode)) {
            return new WebResult(StatusCode.ERROR, false, "短信验证码不正确，请重新输入");
        }
        userService.saveUser(user);
        return new WebResult(StatusCode.OK, true, "注册成功");
    }

    /**
     * 保存
     *
     * @param user
     */
    @PostMapping
    public WebResult add(@RequestBody User user) {
        userService.saveUser(user);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    /**
     * 更新
     *
     * @param user
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.updateUser(user);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        userService.deleteUserById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", userService.findUserList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", userService.findUserById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", userService.findUserList(searchMap));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public WebResult listPage(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageResponse = userService.findUserListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<User>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
