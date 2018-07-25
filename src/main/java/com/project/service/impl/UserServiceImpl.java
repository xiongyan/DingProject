package com.project.service.impl;

import com.project.model.Result;
import com.project.dao.UserDao;
import com.project.enums.ResultEnum;
import com.project.exception.UserException;
import com.project.model.*;
import com.project.service.UserService;
import com.project.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private Md5Util md5Util;

    /**
     * 获取用户列表
     * @return
     */
    @Override
    public Result getUsers() {
        List<User> list = userDao.getUsers();
        if(list != null){
            return ResultUtil.success(list);
        }else{
            throw new UserException(ResultEnum.ERROR);
        }
    }
}
