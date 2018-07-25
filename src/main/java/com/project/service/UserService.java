package com.project.service;

import com.project.dao.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface UserService {
    /**
     * 获取用户列表
     * @return
     */
    Result getUsers();
}

