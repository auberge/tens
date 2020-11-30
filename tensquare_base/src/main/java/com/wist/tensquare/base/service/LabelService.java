package com.wist.tensquare.base.service;

import com.wist.tensquare.base.dao.LabelDao;
import com.wist.tensquare.base.pojo.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LabelService {
    @Resource
    private LabelDao labelDao;
    @Resource
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public List<Label> search(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象，就是要把条件封装到这个对象中，where类名=label.getId
             * @param query 封装查询关键字，如groupBy，orderBy
             * @param cb 用来封装条件对象，如果直接返回null表示无查询条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new 一个list集合，存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //where labelName like "%小明%"
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                //new一个数组作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转成数组
                list.toArray(parr);
                return cb.and(parr);
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        //封装分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new 一个list集合，存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //where labelName like "%小明%"
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                //new一个数组作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转成数组
                list.toArray(parr);
                return cb.and(parr);
            }
        }, pageable);
    }
}
