package com.wist.tensquare.qa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.wist.tensquare.qa.dao.ProblemDao;
import com.wist.tensquare.qa.po.Problem;
import util.IdWorker;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ProblemService {

    @Resource
    private ProblemDao problemDao;
    @Resource
    private IdWorker idWorker;

    public Page<Problem> newList(String labelid, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.newList(labelid, pageable);
    }

    public Page<Problem> hotList(String labelid, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.hotList(labelid, pageable);
    }

    public Page<Problem> waitList(String labelid, int page, int rows) {
        Pageable pageable = PageRequest.of(page - 1, rows);
        return problemDao.waitList(labelid, pageable);
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void saveProblem(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem
     */
    public void updateProblem(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteProblemById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findProblemList() {
        return problemDao.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findProblemById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 根据条件查询列表
     *
     * @param whereMap
     * @return
     */
    public List<Problem> findProblemList(Map whereMap) {
        //构建Spec查询条件
        Specification<Problem> specification = getProblemSpecification(whereMap);
        //Specification条件查询
        return problemDao.findAll(specification);
    }

    /**
     * 组合条件分页查询
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findProblemListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<Problem> specification = getProblemSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }

    /**
     * 根据参数Map获取Spec条件对象
     *
     * @param searchMap
     * @return
     */
    private Specification<Problem> getProblemSpecification(Map searchMap) {

        return (Specification<Problem>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 标题
            if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
            }
            // 内容
            if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
            }
            // 用户ID
            if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }
            // 是否解决
            if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                predicateList.add(cb.like(root.get("solve").as(String.class), "%" + (String) searchMap.get("solve") + "%"));
            }
            // 回复人昵称
            if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + (String) searchMap.get("replyname") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

}
