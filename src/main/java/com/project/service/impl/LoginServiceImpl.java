package com.project.service.impl;

import com.project.dao.UserDao;
import com.project.model.User;
import com.project.service.LoginService;
import com.project.util.Md5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
     * @param userName
     * @param password
     * @return
     */
    @Override
    public User findUserByNameAndPwd(String userName, String password){
        Map<String,String> parameter = new HashMap<>();
        parameter.put("phone",userName);
        parameter.put("password",md5Util.MD5(password));
        User user = userDao.findUserByNameAndPwd(parameter);
        return user;
    }
}
