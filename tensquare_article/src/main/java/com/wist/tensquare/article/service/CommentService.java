package com.wist.tensquare.article.service;

import com.wist.tensquare.article.dao.CommentDao;
import com.wist.tensquare.article.pojo.Comment;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private IdWorker idWorker;

    public void save(Comment comment){
        comment.set_id(idWorker.nextId()+"");
        comment.setPublishDate(new Date());
        commentDao.save(comment);
    }

    public List<Comment> findByArticleId(String articleId) {
        return commentDao.findByArticleId(articleId);
    }

    public void deleteById(String commentId){
        commentDao.deleteById(commentId);
    }
}
