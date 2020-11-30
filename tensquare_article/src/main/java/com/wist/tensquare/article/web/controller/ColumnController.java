package com.wist.tensquare.article.web.controller;

import java.util.Map;

import com.wist.tensquare.article.pojo.Column;
import com.wist.tensquare.article.service.ColumnService;
import entity.PageResult;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController {

    @Resource
    private ColumnService columnService;


    /**
     * 增加
     *
     * @param column
     */
    @PostMapping
    public WebResult add(@RequestBody Column column) {
        columnService.saveColumn(column);
        return new WebResult(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param column
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Column column, @PathVariable String id) {
        column.setId(id);
        columnService.updateColumn(column);
        return new WebResult(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        columnService.deleteColumnById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", columnService.findColumnList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", columnService.findColumnById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", columnService.findColumnList(searchMap));
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
        Page<Column> pageResponse = columnService.findColumnListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Column>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
