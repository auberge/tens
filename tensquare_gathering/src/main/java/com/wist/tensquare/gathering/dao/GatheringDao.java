package com.wist.tensquare.gathering.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wist.tensquare.gathering.pojo.Gathering;
/**
 * 数据访问接口
 * @author simptx
 *
 */
public interface GatheringDao extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{
	
}
