package com.project.service;

import com.project.model.User;

/**
 * Created by laishun on 2018/3/9.
 */
public interface LoginService {

    /**
     * 通过userId查询是否存在这个用户
     * @param userId
     * @return
     */
    User findUserByUserId(String userId);
}
