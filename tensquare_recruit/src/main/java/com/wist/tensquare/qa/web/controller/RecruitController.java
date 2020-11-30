package com.wist.tensquare.qa.web.controller;

import java.util.Map;

import com.wist.tensquare.qa.service.RecruitService;
import entity.PageResult;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.wist.tensquare.qa.pojo.Recruit;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

    @Resource
    private RecruitService recruitService;

    @RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
    public WebResult recommend() {
        return new WebResult(StatusCode.OK, true, "查询成功", recruitService.recommend());
    }

    @RequestMapping(value = "/search/newList", method = RequestMethod.GET)
    public WebResult newList() {
        return new WebResult(StatusCode.OK, true, "查询成功", recruitService.newList());
    }

    /**
     * 增加
     *
     * @param recruit
     */
    @PostMapping
    public WebResult add(@RequestBody Recruit recruit) {
        recruitService.saveRecruit(recruit);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.updateRecruit(recruit);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        recruitService.deleteRecruitById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", recruitService.findRecruitList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", recruitService.findRecruitById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", recruitService.findRecruitList(searchMap));
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
        Page<Recruit> pageResponse = recruitService.findRecruitListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Recruit>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
