package com.wist.tensquare.user.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.wist.tensquare.user.pojo.Admin;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.wist.tensquare.user.service.AdminService;

import entity.PageResult;
import entity.WebResult;
import entity.StatusCode;
import util.JwtUtil;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public WebResult login(@RequestBody Admin admin) {
        admin = adminService.login(admin);
        if (admin==null){
            return new WebResult(StatusCode.LOGINERROR, false, "登陆失败");
        }
        String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("name",admin.getLoginname());
        map.put("roles","admin");
        return new WebResult(StatusCode.OK, true, "登录成功",map);
    }

    /**
     * 保存
     *
     * @param admin
     */
    @PostMapping
    public WebResult add(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    /**
     * 更新
     *
     * @param admin
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.updateAdmin(admin);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        adminService.deleteAdminById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", adminService.findAdminList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", adminService.findAdminById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", adminService.findAdminList(searchMap));
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
        Page<Admin> pageResponse = adminService.findAdminListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Admin>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
