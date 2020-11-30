package com.wist.tensquare.base.controller;

import com.wist.tensquare.base.pojo.Label;
import com.wist.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.WebResult;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
    @Resource
    private LabelService labelService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET)
    public WebResult findAll() {
        System.out.println(request.getHeader("token"));
        return new WebResult(StatusCode.OK, true, "查询成功", labelService.findAll());
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public WebResult findById(@PathVariable String labelId) {
        return new WebResult(StatusCode.OK, true, "查询成功", labelService.findById(labelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public WebResult save(@RequestBody Label label) {
        labelService.save(label);
        return new WebResult(StatusCode.OK, true, "保存成功");
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public WebResult update(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new WebResult(StatusCode.OK, true, "更新成功");
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public WebResult delete(@PathVariable("labelId") String labelId) {
        labelService.deleteById(labelId);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public WebResult findSearch(@RequestBody Label label) {
        List<Label> labelList = labelService.search(label);
        return new WebResult(StatusCode.OK, true, "查询成功", labelList);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public WebResult findSearch(@RequestBody Label label, @PathVariable int page, @PathVariable int size) {
        Page<Label> pageData = labelService.pageQuery(label, page, size);
        PageResult<Label> labelPageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
        return new WebResult(StatusCode.OK, true, "查询成功", labelPageResult);
    }
}
