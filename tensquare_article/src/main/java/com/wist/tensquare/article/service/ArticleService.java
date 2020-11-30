package com.wist.tensquare.article.service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.wist.tensquare.article.dao.ArticleDao;
import com.wist.tensquare.article.pojo.Article;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author simptx
 */
@Service
@Transactional
public class ArticleService {
    @Resource
    private ArticleDao articleDao;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IdWorker idWorker;

    public void updateState(String id) {
        articleDao.updateState(id);
    }

    public void addThumbup(String id) {
        articleDao.addThumbup(id);
    }

    /**
     * 增加
     *
     * @param article
     */
    public void saveArticle(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void updateArticle(Article article) {
        redisTemplate.delete("article"+article.getId());
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteArticleById(String id) {
        redisTemplate.delete("article"+id);
        articleDao.deleteById(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findArticleList() {
        return articleDao.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        if (article == null || article.equals("")) {
            article = articleDao.findById(id).get();
            redisTemplate.opsForValue().set("article_" + id, article,10, TimeUnit.SECONDS);
        }
        return article;
    }

    /**
     * 根据条件查询列表
     *
     * @param whereMap
     * @return
     */
    public List<Article> findArticleList(Map whereMap) {
        //构建Spec查询条件
        Specification<Article> specification = getArticleSpecification(whereMap);
        //Specification条件查询
        return articleDao.findAll(specification);
    }

    /**
     * 组合条件分页查询
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findArticleListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<Article> specification = getArticleSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification, pageRequest);
    }

    /**
     * 根据参数Map获取Spec条件对象
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> getArticleSpecification(Map searchMap) {

        return (Specification<Article>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 专栏ID
            if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid"))) {
                predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + (String) searchMap.get("columnid") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
            }
            // 文章正文
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
            }
            // 文章封面
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
            }
            // 是否公开
            if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic"))) {
                predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + (String) searchMap.get("ispublic") + "%"));
            }
            // 是否置顶
            if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop"))) {
                predicateList.add(cb.like(root.get("istop").as(String.class), "%" + (String) searchMap.get("istop") + "%"));
            }
            // 审核状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
            }
            // 所属频道
            if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid"))) {
                predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + (String) searchMap.get("channelid") + "%"));
            }
            // URL
            if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
            }
            // 类型
            if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                predicateList.add(cb.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

}
