package com.project.controller;

import com.project.service.UserService;
import com.project.util.RequestUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public Object getUsers() {
        return userService.getUsers();
    }

    /**
     * 创建用户信息
     * @param req
     * @return
     */
    @RequestMapping(value="/users",method= RequestMethod.POST)
    public Object createUser(HttpServletRequest req){
        return userService.createUser(req);
    }

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value="/users/{userId}",method= RequestMethod.DELETE)
    public Object deleteUser(@PathVariable int userId){
        return userService.deleteUser(userId);
    }

    /**
     * 查询单个租客
     * @param userId
     * @return
     */
    @RequestMapping(value="/users/{userId}",method= RequestMethod.GET)
    public Object getUser(@PathVariable int userId){
        return userService.getUser(userId);
    }

    /**
     * 修改租客信息
     * @param req
     * @param userId
     * @return
     */
    @RequestMapping(value="/users/{userId}",method= RequestMethod.POST)
    public Object UpdateUser(HttpServletRequest req,@PathVariable int userId){
        return userService.UpdateUser(req, userId);
    }
}
