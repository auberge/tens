package com.wist.tensquare.article.web.controller;

import com.wist.tensquare.article.pojo.Comment;
import com.wist.tensquare.article.service.CommentService;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping
    public WebResult save(@RequestBody Comment comment){
        commentService.save(comment);
        return new WebResult(StatusCode.OK,true,"评论成功");
    }

    @GetMapping(value = "/article/{articleId}")
    public WebResult findByArticleId(@PathVariable String articleId){
        return new WebResult(StatusCode.OK,true,"查询成功",commentService.findByArticleId(articleId));
    }

    @DeleteMapping("/{commentId}")
    public WebResult deleteById(@PathVariable String commentId){
        commentService.deleteById(commentId);
        return new WebResult(StatusCode.OK,true,"删除成功");
    }
}
