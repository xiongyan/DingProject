package com.project.service.impl;

import com.project.dao.DepartmentDao;
import com.project.dao.HouseDao;
import com.project.dao.UserDao;
import com.project.model.*;
import com.project.service.UserService;
import com.project.util.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private HouseDao houseDao;

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
    public Object getUsers(HttpServletRequest req,String kind) {
        User user = CacheUtil.getInstance().getUser(req);
        String role = user.getRole();
        List<User> list = null;
        if(role.equalsIgnoreCase("landlord")){
            list = userDao.getUsersFromLandlord(user.getId());
        }else if(role.equalsIgnoreCase("manager")){
            list = userDao.getUsersFromManager(kind);
        }
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
        int    houseId = res.getIntOrElse("houseId",-1);
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
                userDao.createUser(user_new);
                User newUser = userDao.findUserByPhone(phone);
                if(newUser != null){
                    //和房子绑定关系
                    House house = houseDao.getHouse(houseId);
                    house.setTenant(newUser.getId());
                    houseDao.updateHouse(house);
                    //更新和房东的关系
                    Map<String ,Object> parameter = new HashMap<>();
                    parameter.put("tenant",newUser.getId());
                    parameter.put("landlord",house.getLandlord());
                    userDao.createRelationWithLandlord(parameter);
                    //向钉钉同步创建一个用户
                    DingUtil dingUtil = DingUtil.getInstance();
                    if(role.equalsIgnoreCase("tenant")){
                        Department department = departmentDao.getDepartment("租客部");
                        int department_id;
                        if(department == null){
                            department_id = dingUtil.addDingApartment("租客部");
                            Department newDepartment = new Department();
                            newDepartment.setId(department_id);
                            newDepartment.setTitle("租客部");
                            departmentDao.createDepartment(newDepartment);
                        }else {
                            department_id = department.getId();
                        }
                        if(department_id != -1){
                            dingUtil.addDingUser(phone,name,department_id);
                            code = 200;
                            msg = "创建用户成功";
                            respEntity.setData(user_new);
                        }else{
                            code = 500;
                            msg = "向钉钉创建用户失败";
                        }
                    }else if(role.equalsIgnoreCase("landlord")){
                        Department department = departmentDao.getDepartment("房东部");
                        int department_id;
                        if(department == null){
                            department_id = dingUtil.addDingApartment("房东部");
                            Department newDepartment = new Department();
                            newDepartment.setId(department_id);
                            newDepartment.setTitle("房东部");
                            departmentDao.createDepartment(newDepartment);
                        }else {
                            department_id = department.getId();
                        }
                        if(department_id != -1){
                            dingUtil.addDingUser(phone,name,department_id);
                            code = 200;
                            msg = "创建用户成功";
                            respEntity.setData(user_new);
                        }else{
                            code = 500;
                            msg = "向钉钉创建用户失败";
                        }
                    }
                }else {
                    code = 500;
                    msg = "创建用户失败";
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
     * @param tenantId
     * @return
     */
    public Object deleteUser(String tenantId){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        int res = userDao.deleteUser(Integer.parseInt(tenantId));
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
    public Object getUser(String userId){
        int code = 500;
        String msg = "参数异常";
        if(userId != null && !"".equalsIgnoreCase(userId)){
            User user = userDao.getUser(Integer.parseInt(userId));
            if(user != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(user);
            }else{
                code = 500;
                msg = "找不到该数据";
                respEntity.setData(null);
            }
        }else{
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
    public Object UpdateUser(HttpServletRequest req, String user_id){
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
        if(user_id != null && !"".equalsIgnoreCase(user_id.trim())){
            User user_new = new User();
            user_new.setId(Integer.parseInt(user_id));
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
        }else {
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 给没交房租的用户，发送ding消息
     * @param tenantId
     * @return
     */
    public Object ding(int tenantId){
        User user = userDao.getUser(tenantId);
        List<House> houses = houseDao.getHousesFromTenant(user.getId());
        DingUtil dingUtil = DingUtil.getInstance();
        for(House house:houses){
            String body = "{\"message_url\": \"http://dingtalk.com\",\"head\": {\"bgcolor\": \"FFBBBBBB\",\"text\": \"交房租提醒消息\"},\"body\": {\"title\": \"交房租\",\"form\": [{\"key\": \"租客:\",\"value\": \""+user.getName()+"\"},{\"key\": \"房子:\",\"value\": \""+house.getAddress()+house.getRoom()+"\"}],\"rich\": {\"num\": \""+house.getRent()+"\",\"unit\": \"元\"},\"content\": \"该交房租啦\",\"image\": \"\",\"author\": \"房东 \"}}";
            dingUtil.sendDDMessage(user.getPhone(),body);
        }
        respEntity.setCode(200);
        respEntity.setMsg("ding消息发送成功");
        return respEntity;
    }

    /**
     * 房客退房信息
     * @param landlord
     * @param tenant
     * @return
     */
    public Object checkout(int landlord, int tenant){
        int code = 500;
        String msg = "";
        if(landlord > -1 && tenant > -1){
            try {
                //更新房子对应的租客
                List<House> houses = houseDao.getHousesFromTenant(tenant);
                for (House house:houses){
                    house.setTenant(-1);
                    houseDao.updateHouse(house);
                }
                //更新租客和房东的关系
                Map<String ,Object> parameter = new HashMap<>();
                parameter.put("tenant",tenant);
                parameter.put("landlord",landlord);
                userDao.deleteRelationWithLandlord(parameter);
                code = 200;
                msg = "成功退房";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return respEntity;
    }

    /**
     * 房东预收租金和实际租金信息
     * @param landlordId
     * @return
     */
    public Object totalRents(int landlordId,String startTime,String endTime){
        int code = 500;
        String msg = "内部发生异常";
        if(endTime.compareTo(startTime) > -1 && landlordId > -1){
            //获取预收金额
            double rents = houseDao.totalRents(landlordId);
            int interval = DateUtil.getIntervalMonth(startTime,endTime);
            double payment =(interval+1)*rents;
            //实际收房租
            Map<String ,Object> parameter = new HashMap<>();
            parameter.put("landlord",landlordId);
            parameter.put("startTime",startTime);
            parameter.put("endTime",endTime);
            Account actual = userDao.actualPayment(parameter);
            Payment res = new Payment();
            if(actual == null){
                res.setActual(0.0);
            }else{
                res.setActual(actual.getTotal());
            }
            res.setPayment(payment);
            code = 200;
            msg = "返回成功";
            respEntity.setData(res);
        }else {
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

}
