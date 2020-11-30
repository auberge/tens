package com.wist.tensquare.qa.dao;

import com.wist.tensquare.qa.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {
    /**
     * where state = ? order by createTime
     *
     * @return
     */
    public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
