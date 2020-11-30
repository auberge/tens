package com.wist.tensquare.spit.service;

import com.mongodb.MongoClient;
import com.wist.tensquare.spit.dao.SpitDao;
import com.wist.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author simptx
 */
@Service
@Transactional
public class SpitService {
    @Resource
    private SpitDao spitDao;
    @Resource
    private IdWorker idWorker;
    @Resource
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void save(Spit spit) {
        long id = idWorker.nextId();
        spit.setPublishTime(new Date());//发布时间为当前时间
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        spit.set_id(id + "");
        if (spit.getParentId()!=null&&!"".equals(spit.getParentId())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentId()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentId(parentId, pageable);
    }

    public void thumbup(String spitId) {
        /*
        Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup(spit.getThumbup()==null?0:spit.getThumbup()+1);
        spitDao.save(spit);
        */

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
