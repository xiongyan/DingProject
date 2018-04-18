package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface UserService {
    /**
     * 获取用户列表
     * @return
     */
    Object getUsers();

    /**
     * create a new user
     * @param req
     * @return
     */
    Object createUser(HttpServletRequest req);

    /**
     * delete user
     * @param userId
     * @return
     */
    Object deleteUser(int userId);

    /**
     * get house by id
     * @param userId
     * @return
     */
    Object getUser(int userId);

    /**
     * get house by id
     * @param userId
     * @param req
     * @return
     */
    Object UpdateUser(HttpServletRequest req, int userId);


}

