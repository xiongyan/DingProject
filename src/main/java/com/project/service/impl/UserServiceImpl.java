package com.project.service.impl;

import com.project.dao.DepartmentDao;
import com.project.dao.UserDao;
import com.project.model.*;
import com.project.service.UserService;
import com.project.util.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;



    @Resource
    private DepartmentDao departmentDao;

    @Resource
    private Md5Util md5Util;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    /**
     * 获取用户列表
     * @return
     */
    @Override
    public Object getUsers() {
        List<User> list = userDao.getUsers();
        int code = 500;
        String msg = "内部发生异常";
        if(list != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(list);
        }else{
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * create a new user
     * @param req
     * @return
     */
    @Override
    public Object createUser(HttpServletRequest req){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String name = res.getStringOrElse("name");
        String gender = res.getStringOrElse("gender");
        String email = res.getStringOrElse("email");
        String password = res.getStringOrElse("password");
        String phone = res.getStringOrElse("phone");
        String role = res.getStringOrElse("role");
        String idCard = res.getStringOrElse("idCard");
        String remark = res.getStringOrElse("remark");
        int code = 500;
        String msg = "邮箱不能空";
        if(phone != null && !"".equalsIgnoreCase(phone.trim())){
            User user = userDao.findUserByPhone(phone);
            if(user == null){
                User user_new = new User();
                user_new.setName(name);
                user_new.setEmail(email);
                user_new.setPassword(md5Util.MD5(password));
                user_new.setRole(role);
                user_new.setStatus(1);
                user_new.setIdCard(idCard);
                user_new.setPhone(phone);
                user_new.setRemark(remark);
                user_new.setGender(gender);
                user_new.setInsertDate(DateUtil.getTodayTime());
                //保存新用户
                int flag = userDao.createUser(user_new);
                if(flag == 1){
                    code = 200;
                    msg = "增加新用户成功";
                    respEntity.setData(null);
                }
            }else{
                code = 500;
                msg = "该邮箱已经注册过啦";
                respEntity.setData(null);
            }
        }else {
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * delete user
     * @param userId
     * @return
     */
    public Object deleteUser(int userId){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        int res = userDao.deleteUser(userId);
        if(res == 1){
            code = 200;
            msg = "删除成功";
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return  respEntity;
    }

    /**
     * get house by id
     * @param userId
     * @return
     */
    public Object getUser(int userId){
        int code = 500;
        String msg = "内部异常";
        User user = userDao.getUser(userId);
        if(user != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(user);
        }else{
            code = 500;
            msg = "找不到该数据";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * get house by id
     * @param user_id
     * @param req
     * @return
     */
    public Object UpdateUser(HttpServletRequest req, int user_id){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String name = res.getStringOrElse("name");
        String gender = res.getStringOrElse("gender");
        String email = res.getStringOrElse("email");
        String password = res.getStringOrElse("password");
        String phone = res.getStringOrElse("phone");
        int status = res.getIntOrElse("status", 1);
        String role = res.getStringOrElse("role");
        String remark = res.getStringOrElse("remark");
        String idCard = res.getStringOrElse("idCard");
        int code = 500;
        String msg = "用户ID不能空";
        User user_new = new User();
        user_new.setId(user_id);
        user_new.setName(name);
        user_new.setEmail(email);
        if(password != null && !password.equalsIgnoreCase("")){
            user_new.setPassword(md5Util.MD5(password));
        }
        user_new.setRole(role);
        user_new.setStatus(status);
        user_new.setPhone(phone);
        user_new.setRemark(remark);
        user_new.setGender(gender);
        user_new.setIdCard(idCard);
        int flag = userDao.updateUser(user_new);
        if(flag == 1){
            code = 200;
            msg = "更新用户成功";
            respEntity.setData(user_new);
        }else {
            code = 500;
            msg = "更新用户失败";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }
}
