package com.wist.tensquare.spit.dao;

import com.wist.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitDao extends MongoRepository<Spit, String> {

    public Page<Spit> findByParentId(String parentid, Pageable pageable);
}
