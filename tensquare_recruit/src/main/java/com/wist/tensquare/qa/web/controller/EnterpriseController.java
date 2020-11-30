package com.wist.tensquare.qa.web.controller;

import java.util.List;
import java.util.Map;

import com.wist.tensquare.qa.service.EnterpriseService;
import entity.PageResult;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.wist.tensquare.qa.pojo.Enterprise;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Resource
    private EnterpriseService enterpriseService;

    @RequestMapping(value = "/search/hotlist", method = RequestMethod.GET)
    public WebResult hotList() {
        List<Enterprise> enterpriseList = enterpriseService.hotList("1");
        return new WebResult(StatusCode.OK, true, "查询成功", enterpriseList);
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    @PostMapping
    public WebResult add(@RequestBody Enterprise enterprise) {
        enterpriseService.saveEnterprise(enterprise);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Enterprise enterprise, @PathVariable String id) {
        enterprise.setId(id);
        enterpriseService.updateEnterprise(enterprise);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        enterpriseService.deleteEnterpriseById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", enterpriseService.findEnterpriseList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", enterpriseService.findEnterpriseById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", enterpriseService.findEnterpriseList(searchMap));
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
        Page<Enterprise> pageResponse = enterpriseService.findEnterpriseListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Enterprise>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
