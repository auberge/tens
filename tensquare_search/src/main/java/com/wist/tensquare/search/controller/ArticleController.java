package com.wist.tensquare.search.controller;

import com.wist.tensquare.search.pojo.Article;
import com.wist.tensquare.search.service.ArticleService;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/search")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    public WebResult save(@RequestBody Article article) {
        articleService.save(article);
        return new WebResult(StatusCode.OK, true, "添加成功");
    }

    @GetMapping(value = "/article/{key}/{page}/{size}")
    public WebResult findByKey(@PathVariable String key, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageData = articleService.findByKey(key, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功",pageData);
    }
}
