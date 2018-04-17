package com.project.controller;

import com.project.model.User;
import com.project.service.UserService;
import com.project.util.CacheUtil;
import com.project.util.RequestUtil;
import org.json.JSONObject;
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

    @Resource
    private RequestUtil requestUtil;

    /**
     * 租客信息查询
     * @return
     */
    @RequestMapping(value="/tenants",method= RequestMethod.GET)
    public Object getTenants(HttpServletRequest req) {
        return userService.getUsers(req,"tenant");
    }

    /**
     * 创建租客信息
     * @param req
     * @return
     */
    @RequestMapping(value="/tenants",method= RequestMethod.POST)
    public Object createTenant(HttpServletRequest req){
        return userService.createUser(req);
    }

    /**
     * 删除租客信息
     * @param tenantId
     * @return
     */
    @RequestMapping(value="/tenants/{tenantId}",method= RequestMethod.DELETE)
    public Object deleteTenant(@PathVariable String tenantId){
        return userService.deleteUser(tenantId);
    }

    /**
     * 查询单个租客
     * @param tenantId
     * @return
     */
    @RequestMapping(value="/tenants/{tenantId}",method= RequestMethod.GET)
    public Object getTenant(@PathVariable String tenantId){
        return userService.getUser(tenantId);
    }

    /**
     * 修改租客信息
     * @param req
     * @param tenantId
     * @return
     */
    @RequestMapping(value="/tenants/{tenantId}",method= RequestMethod.POST)
    public Object UpdateTenant(HttpServletRequest req,@PathVariable String tenantId){
        return userService.UpdateUser(req, tenantId);
    }

    /**
     * 给没交房租的用户，发送ding消息
     * @param tenantId
     * @return
     */
    @RequestMapping(value="/ding/{tenantId}",method= RequestMethod.GET)
    public Object ding(@PathVariable int tenantId){
        return userService.ding(tenantId);
    }

    /**
     * 房东信息查询
     * @return
     */
    @RequestMapping(value="/landlords",method= RequestMethod.GET)
    public Object getLandlords(HttpServletRequest req) {
        return userService.getUsers(req,"landlord");
    }

    /**
     * 创建房东信息
     * @param req
     * @return
     */
    @RequestMapping(value="/landlords",method= RequestMethod.POST)
    public Object createLandlord(HttpServletRequest req){
        return userService.createUser(req);
    }

    /**
     * 删除租客信息
     * @param landlordId
     * @return
     */
    @RequestMapping(value="/landlords/{landlordId}",method= RequestMethod.DELETE)
    public Object deleteLandlord(@PathVariable String landlordId){
        return userService.deleteUser(landlordId);
    }

    /**
     * 查询单个房东
     * @param landlordId
     * @return
     */
    @RequestMapping(value="/landlords/{landlordId}",method= RequestMethod.GET)
    public Object getLandlord(@PathVariable String landlordId){
        return userService.getUser(landlordId);
    }

    /**
     * 修改房东信息
     * @param req
     * @param landlordId
     * @return
     */
    @RequestMapping(value="/landlords/{landlordId}",method= RequestMethod.POST)
    public Object UpdateLandlord(HttpServletRequest req,@PathVariable String landlordId){
        return userService.UpdateUser(req, landlordId);
    }

    /**
     * 房客退房信息
     * @param req
     * @param userId
     * @return
     */
    @RequestMapping(value="/landlords/checkout/{userId}",method= RequestMethod.GET)
    public Object checkout(HttpServletRequest req,@PathVariable int userId){
        User user = CacheUtil.getInstance().getUser(req);
        return userService.checkout(user.getId(), userId);
    }

    /**
     * 房东预收租金和实际租金信息
     * @param landlordId
     * @return
     */
    @RequestMapping(value="/landlords/rent/{landlordId}",method= RequestMethod.POST)
    public Object totalRents(HttpServletRequest req,@PathVariable int landlordId){
        JSONObject json = requestUtil.getBody(req);
        String startTime = json.getString("startTime");
        String endTime = json.getString("endTime");
        return userService.totalRents(landlordId,startTime,endTime);
    }

}
