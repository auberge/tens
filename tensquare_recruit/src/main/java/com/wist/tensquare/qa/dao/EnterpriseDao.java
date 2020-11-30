package com.wist.tensquare.qa.dao;

import com.wist.tensquare.qa.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {
    /**
     * 封装查询条件
     * where ishot=?
     *
     * @return
     */
    public List<Enterprise> findByIshot(String ishot);

}
