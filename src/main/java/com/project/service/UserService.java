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
    Object getUsers(HttpServletRequest req, String kind);

    /**
     * create a new user
     * @param req
     * @return
     */
    Object createUser(HttpServletRequest req);

    /**
     * delete user
     * @param tenantId
     * @return
     */
    Object deleteUser(String tenantId);

    /**
     * get house by id
     * @param userId
     * @return
     */
    Object getUser(String userId);

    /**
     * get house by id
     * @param userId
     * @param req
     * @return
     */
    Object UpdateUser(HttpServletRequest req, String userId);

    /**
     * 给没交房租的用户，发送ding消息
     * @param tenantId
     * @return
     */
    Object ding(int tenantId);

    /**
     * 房客退房信息
     * @param landlord
     * @param tenant
     * @return
     */
    Object checkout(int landlord, int tenant);

    /**
     * 房东预收租金和实际租金信息
     * @param landlordId
     * @param startTime
     * @param endTime
     * @return
     */
    Object totalRents(int landlordId, String startTime, String endTime);


}

