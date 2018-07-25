package com.project.controller;

import com.project.model.Result;
import com.project.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户信息查询
     * @return
     */
    @RequestMapping(value="/users",method= RequestMethod.GET)
    public Result getUsers() throws Exception{
        return userService.getUsers();
    }
}
