package com.wist.tensquare.qa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.wist.tensquare.qa.dao.ReplyDao;
import com.wist.tensquare.qa.po.Reply;
import util.IdWorker;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ReplyService {

    @Resource
    private ReplyDao replyDao;

    @Resource
    private IdWorker idWorker;

    /**
     * 增加
     *
     * @param reply
     */
    public void saveReply(Reply reply) {
        reply.setId(idWorker.nextId() + "");
        replyDao.save(reply);
    }

    /**
     * 修改
     *
     * @param reply
     */
    public void updateReply(Reply reply) {
        replyDao.save(reply);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteReplyById(String id) {
        replyDao.deleteById(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Reply> findReplyList() {
        return replyDao.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Reply findReplyById(String id) {
        return replyDao.findById(id).get();
    }

    /**
     * 根据条件查询列表
     *
     * @param whereMap
     * @return
     */
    public List<Reply> findReplyList(Map whereMap) {
        //构建Spec查询条件
        Specification<Reply> specification = getReplySpecification(whereMap);
        //Specification条件查询
        return replyDao.findAll(specification);
    }

    /**
     * 组合条件分页查询
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Reply> findReplyListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<Reply> specification = getReplySpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return replyDao.findAll(specification, pageRequest);
    }

    /**
     * 根据参数Map获取Spec条件对象
     *
     * @param searchMap
     * @return
     */
    private Specification<Reply> getReplySpecification(Map searchMap) {

        return (Specification<Reply>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // 编号
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 问题ID
            if (searchMap.get("problemid") != null && !"".equals(searchMap.get("problemid"))) {
                predicateList.add(cb.like(root.get("problemid").as(String.class), "%" + (String) searchMap.get("problemid") + "%"));
            }
            // 回答内容
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
            }
            // 回答人ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
            }
            // 回答人昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

}
