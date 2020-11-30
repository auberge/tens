package com.wist.tensquare.article.web.controller;

import java.util.Map;

import com.wist.tensquare.article.pojo.Article;
import com.wist.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.StatusCode;
import entity.WebResult;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author simptx
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/examine/{articleId}", method = RequestMethod.PUT)
    public WebResult examine(@PathVariable String articleId) {
        System.out.println(articleId);
        articleService.updateState(articleId);
        return new WebResult(StatusCode.OK, true, "审核成功");
    }

    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.PUT)
    public WebResult thumbup(@PathVariable String articleId) {
        articleService.addThumbup(articleId);
        return new WebResult(StatusCode.OK, true, "点赞成功");
    }

    /**
     * 增加
     *
     * @param article
     */
    @PostMapping
    public WebResult add(@RequestBody Article article) {
        articleService.saveArticle(article);
        return new WebResult(StatusCode.OK, true, "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @PutMapping("/{id}")
    public WebResult edit(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.updateArticle(article);
        return new WebResult(StatusCode.OK, true, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public WebResult remove(@PathVariable String id) {
        articleService.deleteArticleById(id);
        return new WebResult(StatusCode.OK, true, "删除成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public WebResult list() {
        return new WebResult(StatusCode.OK, true, "查询成功", articleService.findArticleList());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public WebResult findById(@PathVariable String id) {
        return new WebResult(StatusCode.OK, true, "查询成功", articleService.findById(id));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public WebResult list(@RequestBody Map searchMap) {
        return new WebResult(StatusCode.OK, true, "查询成功", articleService.findArticleList(searchMap));
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
        Page<Article> pageResponse = articleService.findArticleListPage(searchMap, page, size);
        return new WebResult(StatusCode.OK, true, "查询成功", new PageResult<Article>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

}
