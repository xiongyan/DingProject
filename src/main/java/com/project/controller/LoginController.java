package com.project.controller;

import com.project.model.RespEntity;
import com.project.model.User;
import com.project.service.LoginService;
import com.project.util.CacheUtil;
import com.project.util.Md5Util;
import com.project.util.RequestUtil;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private Md5Util md5Util;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity;


    @RequestMapping(value="/login",method= RequestMethod.POST)
    public Object login(HttpServletRequest req) {
        //获取用户的登陆信息，并且进行认证
        JSONObject res = requestUtil.getBody(req);
        String username = res.getString("phone");
        String password = res.getString("password");
        String token_access;
        if (username != null && password != null){
            //表示用户未退出
            Object token_old = CacheUtil.getInstance().get(username);
            if(token_old != null && !"".contentEquals(token_old.toString())){
                User user_old = (User)CacheUtil.getInstance().get(token_old.toString());
                if(user_old != null){
                    respEntity.setCode(200);
                    respEntity.setMsg("登陆成功");
                    respEntity.setData(user_old);
                    return respEntity;
                }else{
                    CacheUtil.getInstance().remove(username);
                }
            }
            //缓存中不存在这个用户
            User user = loginService.findUserByNameAndPwd(username, password);
            if (user != null){
                Long loginTime = System.currentTimeMillis();
                token_access = md5Util.MD5("userName:"+username+";loginTime:"+loginTime);
                user.setToken(token_access);
                CacheUtil.getInstance().put(username,token_access);
                CacheUtil.getInstance().put(token_access, user);
                respEntity.setCode(200);
                respEntity.setMsg("登陆成功");
                respEntity.setData(user);
                return respEntity;
            }else {
                respEntity.setData(null);
            }
        }
        respEntity.setCode(500);
        respEntity.setMsg("用户名或密码错误");
        return  respEntity;
    }


    @RequestMapping(value = "/logout", method= RequestMethod.POST)
    public Object logout(HttpServletRequest req) {
        // 移除session
        String token_access = req.getHeader("token");
        User user = (User)CacheUtil.getInstance().get(token_access);
        CacheUtil.getInstance().remove(token_access);
        CacheUtil.getInstance().remove(user.getPhone());
        respEntity.setCode(200);
        respEntity.setMsg("成功退出");
        respEntity.setData(null);
        return respEntity;
    }
}
