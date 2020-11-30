package com.wist.tensquare.user.service;

import com.wist.tensquare.user.dao.AdminDao;
import com.wist.tensquare.user.pojo.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import util.IdWorker;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class AdminService {
    @Resource
    private AdminDao adminDao;
    @Resource
    private IdWorker idWorker;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     *
     * @param admin
     * @return
     */
    public Admin login(Admin admin) {
        String loginName = admin.getLoginname();
        Admin adminLogin = adminDao.findByLoginname(loginName);
        if (adminLogin != null&&passwordEncoder.matches(admin.getPassword(),adminLogin.getPassword())) {
            return adminLogin;
        }
        return null;
    }

    /**
     * 增加
     *
     * @param admin
     */
    public void saveAdmin(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        String encodePassword = passwordEncoder.encode(admin.getPassword());
        System.out.println("密码" + encodePassword);
        admin.setPassword(encodePassword);
        adminDao.save(admin);
    }

    /**
     * 修改
     *
     * @param admin
     */
    public void updateAdmin(Admin admin) {
        adminDao.save(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteAdminById(String id) {
        adminDao.deleteById(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Admin> findAdminList() {
        return adminDao.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Admin findAdminById(String id) {
        return adminDao.findById(id).get();
    }

    /**
     * 根据条件查询列表
     *
     * @param whereMap
     * @return
     */
    public List<Admin> findAdminList(Map whereMap) {
        //构建Spec查询条件
        Specification<Admin> specification = getAdminSpecification(whereMap);
        //Specification条件查询
        return adminDao.findAll(specification);
    }

    /**
     * 组合条件分页查询
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Admin> findAdminListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<Admin> specification = getAdminSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminDao.findAll(specification, pageRequest);
    }

    /**
     * 根据参数Map获取Spec条件对象
     *
     * @param searchMap
     * @return
     */
    private Specification<Admin> getAdminSpecification(Map searchMap) {

        return (Specification<Admin>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 登陆名称
            if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

}
