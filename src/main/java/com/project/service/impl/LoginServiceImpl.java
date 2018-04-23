package com.project.service.impl;

import com.project.dao.UserDao;
import com.project.model.User;
import com.project.service.LoginService;
import com.project.util.Md5Util;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created by laishun on 2018/3/12.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;

    @Resource
    private Md5Util md5Util;

    /**
     * 通过姓名和密码查询是否存在这个用户
     * @param userId
     * @return
     */
    @Override
    public User findUserByUserId(String userId){
        User user = userDao.findUserByUserId(userId);
        return user;
    }
}
