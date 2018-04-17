package com.project.service.impl;

import com.project.dao.HouseDao;
import com.project.model.House;
import com.project.model.RespEntity;
import com.project.model.User;
import com.project.service.HouseService;
import com.project.util.CacheUtil;
import com.project.util.DateUtil;
import com.project.util.JsonUtil;
import com.project.util.RequestUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("houseService")
public class HouseServiceImpl implements HouseService {

    @Resource
    private HouseDao houseDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    /**
     * 获取house列表
     * @return
     */
    @Override
    public Object getHouses(HttpServletRequest req) {
        int code = 500;
        String msg = "内部发生异常";
        List<House> list = null;
        User user = CacheUtil.getInstance().getUser(req);
        String role = user.getRole();
        if(role.equalsIgnoreCase("landlord")){
            list = houseDao.getHousesFromLandlord(user.getId());
        }else if(role.equalsIgnoreCase("manager")){
            list = houseDao.getHousesFromManager();
        }else if(role.equalsIgnoreCase("tenant")){
            list = houseDao.getHousesFromTenant(user.getId());
        }
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
    public Object createHouse(HttpServletRequest req){
        int code;
        String msg;
        User user = CacheUtil.getInstance().getUser(req);
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);

        House house_new = new House();
        house_new.setArea(res.getStringOrElse("area"));
        house_new.setLayer(res.getStringOrElse("layer"));
        house_new.setRoom(res.getStringOrElse("room"));
        house_new.setStructure(res.getStringOrElse("structure"));
        house_new.setAddress(res.getStringOrElse("address"));
        house_new.setTitle(res.getStringOrElse("title"));
        house_new.setRemark(res.getStringOrElse("remark"));
        house_new.setPicture(res.getStringOrElse("picture"));
        house_new.setStatus(0);
        house_new.setRent(res.getDoubbleOrElse("rent", 0.0));
        house_new.setLandlord(user.getId());
        house_new.setTenant(-1);
        house_new.setTime(DateUtil.getTodayTime());
        int flag = houseDao.createHouse(house_new);
        if(flag == 1){
            code = 200;
            msg = "创建新房屋成功";
            respEntity.setData(house_new);
        }else {
            code = 500;
            msg = "创建新房屋失败";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * delete user
     * @param houseId
     * @return
     */
    public Object deleteHouse(String houseId){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        int res = houseDao.deleteHouse(Integer.parseInt(houseId));
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
     * @param houseId
     * @return
     */
    public Object getHouse(String houseId){
        int code = 500;
        String msg = "参数异常";
        if(houseId != null && !"".equalsIgnoreCase(houseId)){
            House house = houseDao.getHouse(Integer.parseInt(houseId));
            if(house != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(house);
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
     * @param house_id
     * @return
     */
    public Object UpdateHouse(HttpServletRequest req,String house_id){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        int code = 500;
        String msg = "资产ID不能空";
        if(house_id != null && !"".equalsIgnoreCase(house_id.trim())){
            House house_new = new House();
            house_new.setId(res.getIntOrElse("id", CacheUtil.getInstance().getUser(req).getId()));
            house_new.setArea(res.getStringOrElse("area"));
            house_new.setLayer(res.getStringOrElse("layer"));
            house_new.setRoom(res.getStringOrElse("room"));
            house_new.setStructure(res.getStringOrElse("structure"));
            house_new.setAddress(res.getStringOrElse("address"));
            house_new.setTitle(res.getStringOrElse("title"));
            house_new.setRemark(res.getStringOrElse("remark"));
            house_new.setStatus(res.getIntOrElse("status",0));
            house_new.setTenant(res.getIntOrElse("tenant", 0));
            house_new.setTime(res.getStringOrElse("time"));
            house_new.setPicture(res.getStringOrElse("picture"));
            house_new.setRent(res.getDoubbleOrElse("rent", 0.0));
            house_new.setTime(DateUtil.getTodayTime());
            int flag = houseDao.updateHouse(house_new);
            if(flag == 1){
                code = 200;
                msg = "更新资产成功";
                respEntity.setData(house_new);
            }else {
                code = 500;
                msg = "更新资产失败";
                respEntity.setData(null);
            }
        }else {
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }
}
