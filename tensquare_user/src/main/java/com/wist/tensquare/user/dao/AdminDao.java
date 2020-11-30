package com.wist.tensquare.user.dao;

import com.wist.tensquare.user.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 *
 * @author simptx
 */
public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {
    Admin findByLoginname(String loginName);
}
