package com.wist.tensquare.user.dao;

import com.wist.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByMobile(String mobile);

    @Modifying
    @Query(value = "update tb_user set fanscount=fanscount+? where id=?", nativeQuery = true)
    void updatefanscount(int x, String friendId);

    @Modifying
    @Query(value = "update tb_user set followcount = followcount+? where id=?", nativeQuery = true)
    void updatefollowcount(int x, String userid);
}