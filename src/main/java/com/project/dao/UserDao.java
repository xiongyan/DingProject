package com.project.dao;

import com.project.model.Account;
import com.project.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface UserDao {
    /**
     * 获取用户列表
     * @return
     */
    List<User> getUsersFromLandlord(int landlord);

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUsersFromManager(String role);

    /**
     * if user exit by email
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

    /**
     * if user exit by id
     * @param id
     * @return
     */
    User getUser(int id);

    /**
     * 用户登陆查询
     * @param parameter
     * @return
     */
    User findUserByNameAndPwd(Map<String, String> parameter);

    /**
     * create a new user
     * @param user
     * @return
     */
    int createUser(User user);

    /**
     * delete user
     * @param userId
     * @return
     */
    int deleteUser(int userId);

    /**
     * delete user
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 绑定用户和房东的关系
     * @param parameter
     * @return
     */
    int createRelationWithLandlord(Map<String, Object> parameter);

    /**
     * delete用户和房东的关系
     * @param parameter
     * @return
     */
    int deleteRelationWithLandlord(Map<String, Object> parameter);

    /**
     * 实际房东收房租金额
     * @param parameter
     * @return
     */
    Account actualPayment(Map<String, Object> parameter);
}
