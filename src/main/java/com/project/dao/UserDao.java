package com.project.dao;

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
    List<User> getUsers();

    /**
     * if user exit by email
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

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
     * if user exit by id
     * @param id
     * @return
     */
    User getUser(int id);

    /**
     * delete user
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 用户正确性检验
     * @param userId
     * @return
     */
    User findUserByUserId(String userId);
}
