package com.wist.tensquare.gathering.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import com.wist.tensquare.gathering.service.GatheringService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.wist.tensquare.gathering.pojo.Gathering;

import entity.PageResult;
import entity.WebResult;
import entity.StatusCode;

/**
 * 控制器层
 *
 * @author simptx
 */
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

    @Resource
    private GatheringService gatheringService;


    /**
     * 保存
     *
     * @param gathering
     */
    @PostMapping
    public WebResult add(@RequestBody Gathering gathering) {
        gatheringService.saveGathering(gathering);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    /**
     * 更新
     *
     * @param gathering
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Gathering gathering, @PathVariable String id) {
        gathering.setId(id);
        gatheringService.updateGathering(gathering);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        gatheringService.deleteGatheringById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", gatheringService.findGatheringList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", gatheringService.findGatheringById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", gatheringService.findGatheringList(searchMap));
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
        Page<Gathering> pageResponse = gatheringService.findGatheringListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Gathering>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
