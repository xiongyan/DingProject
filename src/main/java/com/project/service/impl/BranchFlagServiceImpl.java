package com.project.service.impl;

import com.project.dao.BranchFlagDao;
import com.project.model.*;
import com.project.service.BranchFlagService;
import com.project.util.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("branchFlagService")
public class BranchFlagServiceImpl implements BranchFlagService {

    @Resource
    private BranchFlagDao branchFlagDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    /**
     * 获取每个支部的详情信息列表
     * @return
     */
    @Override
    public Object getBranchFlags() {
        int code = 500;
        String msg = "内部发生异常";
        List<Branch> list = branchFlagDao.getBranchFlags();
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
     * 创建一个支部的详情信息
     * @param req
     * @return
     */
    @Override
    public Object createBranchFlag(HttpServletRequest req){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String name = res.getStringOrElse("name");
        String address = res.getStringOrElse("address");
        String longitude = res.getStringOrElse("longitude");
        String latitude = res.getStringOrElse("latitude");
        String details = res.getStringOrElse("details");
        boolean flag = res.getBooleanOrElse("flag", false);
        int code = 500;
        String msg = "新增支部信息失败";
        if(longitude != null && !"".equalsIgnoreCase(longitude) && latitude != null && !"".equalsIgnoreCase(latitude)){
            Branch branch = new Branch();
            branch.setName(name);
            branch.setAddress(address);
            branch.setLongitude(longitude);
            branch.setLatitude(latitude);
            branch.setDetails(details);
            branch.setFlag(flag);
            int index = branchFlagDao.createBranchFlag(branch);
            if(index == 1){
                code = 200;
                msg = "新增支部信息成功";
            }
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return  respEntity;
    }

    /**
     * delete 支部的详情信息
     * @param branchId
     * @return
     */
    public Object deleteBranch(int branchId){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        int res = branchFlagDao.deleteBranch(branchId);
        if (res == 1){
            code = 200;
            msg = "删除成功";
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return  respEntity;
    }

    /**
     * get某个支部的详情信息
     * @param branchId
     * @return
     */
    public Object getBranch(int branchId){
        int code = 500;
        String msg = "参数异常";
        if(branchId != 0){
            Branch branch = branchFlagDao.getBranch(branchId);
            if(branch != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(branch);
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
     * 修改支部的详情信息
     * @param branchId
     * @param req
     * @return
     */
    public Object UpdateBranch(HttpServletRequest req, int branchId){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String name = res.getStringOrElse("name");
        String address = res.getStringOrElse("address");
        String longitude = res.getStringOrElse("longitude");
        String latitude = res.getStringOrElse("latitude");
        String details = res.getStringOrElse("details");
        boolean flag_bool = res.getBooleanOrElse("flag", false);
        int code = 500;
        String msg = "参数异常";
        if(branchId != 0){
            Branch branch = new Branch();
            branch.setId(branchId);
            branch.setName(name);
            branch.setAddress(address);
            branch.setLongitude(longitude);
            branch.setLatitude(latitude);
            branch.setDetails(details);
            branch.setFlag(flag_bool);
            int flag = branchFlagDao.UpdateBranch(branch);
            if(flag == 1){
                code = 200;
                msg = "更新用户成功";
                respEntity.setData(branch);
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

}
