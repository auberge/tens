package com.wist.tensquare.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.Predicate;

import com.wist.tensquare.user.dao.UserDao;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.wist.tensquare.user.pojo.User;
import util.JwtUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IdWorker idWorker;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 更新粉丝数和关注数
     * @param userId
     * @param friendId
     */
    public void updatefanscountandfollowcount(String userId, String friendId,int x) {
        userDao.updatefanscount(x,friendId);
        userDao.updatefollowcount(x,userId);
    }

    /**
     * 用户登录
     *
     * @param mobile
     * @param password
     * @return
     */
    public User login(String mobile, String password) {
        User user = userDao.findByMobile(mobile);
        if (user != null && passwordEncoder.matches(password,user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public void sendSms(String mobile) {
        //生成随机数6位
        String checkCode = RandomStringUtils.randomNumeric(6);
        //将验证码存入缓存
        redisTemplate.opsForValue().set("checkCode" + mobile, checkCode, 30, TimeUnit.MINUTES);
        //发送给用户
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkCode", checkCode);
//        rabbitTemplate.convertAndSend("sms", map);
        System.out.println("短信验证码为：" + checkCode);
    }

    /**
     * 增加
     *
     * @param user
     */
    public void saveUser(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setId(idWorker.nextId() + "");
        user.setFollowcount(0);
        user.setFanscount(0);
        user.setOnline(0L);
        user.setRegdate(new Date());
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void updateUser(User user) {
        userDao.save(user);
    }

    /**
     * 删除 必须由管理员角色
     *
     * @param id
     */
    public void deleteUserById(String id) {
        Object token = request.getAttribute("claims_admin");
        System.out.println(token);
        if (token==null||"".equals(token)){
            throw new RuntimeException("权限不足");
        }
        userDao.deleteById(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findUserList() {
        return userDao.findAll();
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findUserById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 根据条件查询列表
     *
     * @param whereMap
     * @return
     */
    public List<User> findUserList(Map whereMap) {
        //构建Spec查询条件
        Specification<User> specification = getUserSpecification(whereMap);
        //Specification条件查询
        return userDao.findAll(specification);
    }

    /**
     * 组合条件分页查询
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findUserListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<User> specification = getUserSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }

    /**
     * 根据参数Map获取Spec条件对象
     *
     * @param searchMap
     * @return
     */
    private Specification<User> getUserSpecification(Map searchMap) {

        return (Specification<User>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

}
