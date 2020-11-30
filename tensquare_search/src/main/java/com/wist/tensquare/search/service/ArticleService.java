package com.wist.tensquare.search.service;

import com.wist.tensquare.search.dao.ArticleDao;
import com.wist.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleService {
    @Resource
    private ArticleDao articleDao;

    public void save(Article article) {
        articleDao.save(article);
    }

    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(key, key, pageable);
    }

}
