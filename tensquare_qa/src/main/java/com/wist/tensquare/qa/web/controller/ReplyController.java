package com.wist.tensquare.qa.web.controller;

import java.util.Map;

import com.wist.tensquare.qa.po.Reply;
import com.wist.tensquare.qa.service.ReplyService;
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
@RequestMapping("/reply")
public class ReplyController {

    @Resource
    private ReplyService replyService;


    /**
     * 增加
     *
     * @param reply
     */
    @PostMapping
    public WebResult add(@RequestBody Reply reply) {
        replyService.saveReply(reply);
        return new WebResult(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param reply
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Reply reply, @PathVariable String id) {
        reply.setId(id);
        replyService.updateReply(reply);
        return new WebResult(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        replyService.deleteReplyById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", replyService.findReplyList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult listById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", replyService.findReplyById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", replyService.findReplyList(searchMap));
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
        Page<Reply> pageResponse = replyService.findReplyListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Reply>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
