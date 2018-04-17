package com.project.service;

import com.project.model.User;

/**
 * Created by laishun on 2018/3/9.
 */
public interface LoginService {

    /**
     * 通过姓名和密码查询是否存在这个用户
     * @param userName
     * @param password
     * @return
     */
    User findUserByNameAndPwd(String userName, String password);
}
