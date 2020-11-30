package com.wist.tensquare.article.dao;

import com.wist.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment,String> {
    List<Comment> findByArticleId(String articleId);
}
