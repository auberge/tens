package com.wist.tensquare.qa.web.controller;

import java.util.Map;

import com.wist.tensquare.qa.client.BaseClient;
import com.wist.tensquare.qa.po.Problem;
import com.wist.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author BoBoLaoShi
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private ProblemService problemService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private BaseClient baseClient;

    @GetMapping(value = "/label/{labelId}")
    public WebResult findByLabelId(@PathVariable String labelId){
        return baseClient.findById(labelId);
    }

    @RequestMapping(value = "/newlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public WebResult newList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.newList(labelid, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/hotlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public WebResult hotList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.hotList(labelid, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @RequestMapping(value = "/waitlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public WebResult waitList(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.waitList(labelid, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }


    /**
     * 增加
     *
     * @param problem
     */
    @PostMapping
    public WebResult add(@RequestBody Problem problem) {
        Object token = request.getAttribute("claims_user");
        System.out.println(token);
        if (token==null||"".equals(token)){
            return new WebResult(StatusCode.ACCESSERROR, false, "权限不足");
        }
        problemService.saveProblem(problem);
        return new WebResult(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.updateProblem(problem);
        return new WebResult(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        problemService.deleteProblemById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", problemService.findProblemList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", problemService.findProblemById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", problemService.findProblemList(searchMap));
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
        Page<Problem> pageResponse = problemService.findProblemListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Problem>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
