package com.project.service.impl;

import com.project.dao.AuditDao;
import com.project.model.Audit;
import com.project.model.JobInfo;
import com.project.model.RespEntity;
import com.project.service.AuditService;
import com.project.util.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("auditService")
public class AuditServiceImpl implements AuditService {

    @Resource
    private AuditDao auditDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    /**
     * 获取所有工作审核
     * @return
     */
    @Override
    public Object getAudits() {
        List<Audit> list = auditDao.getAudits();
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
     * create一条工作审核
     * @param req
     * @return
     */
    @Override
    public Object createAudit(HttpServletRequest req){
        int code = 500;
        String msg = "邮箱不能空";
        try{
            JSONObject jsonObject = requestUtil.getBody(req);
            JsonUtil res = new JsonUtil(jsonObject);
            int jobInfoId = res.getIntOrElse("jobInfoId", -1);
            String userName = res.getStringOrElse("userName");
            String userId = res.getStringOrElse("userId");
            Audit audit_old = auditDao.findAuditByJobInfoId(jobInfoId);
            if(audit_old == null){
                //create a new audit
                Audit audit = new Audit();
                audit.setJobInfoId(jobInfoId);
                audit.setUserId(userId);
                audit.setUserName(userName);
                audit.setTime(DateUtil.getTodayTime());
                //判断用户是什么level提交的
                String token = req.getHeader("token");
                JsonUtil userJon = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
                //TODO 根据用户的部门来生成认证的流程
                String workPlace = userJon.getStringOrElse("workPlace");
                if("镇".equalsIgnoreCase(workPlace)){
                    audit.setTownAuth(false);
                    audit.setCountyAuth(false);
                    audit.setCityAuth(false);
                    //TODO 向镇级管理人员发送审核钉钉信息
                    DingUtil.getInstance().sendDDMessage("","");
                }else if("县".equalsIgnoreCase(workPlace)){
                    audit.setTownAuth(true);
                    audit.setCountyAuth(false);
                    audit.setCityAuth(false);
                    //TODO 向县级管理人员发送审核钉钉信息
                    DingUtil.getInstance().sendDDMessage("","");
                }else{
                    audit.setTownAuth(true);
                    audit.setCountyAuth(true);
                    audit.setCityAuth(false);
                    //TODO 向市级管理人员发送审核钉钉信息
                    DingUtil.getInstance().sendDDMessage("","");
                }
                //保存新用户
                int flag = auditDao.createAudit(audit);
                if(flag == 1){
                    code = 200;
                    msg = "增加新的工作审核成功";
                    respEntity.setData(null);
                }
            }else{
                code = 500;
                msg = "该工作报告已经正在审核或是已经审核完成啦";
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * delete一条工作审核
     * @param audit
     * @return
     */
    public Object deleteAudit(int audit){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        int res = auditDao.deleteAudit(audit);
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
     * get 所有一条工作审核
     * @param audit
     * @return
     */
    public Object getAudit(int audit){
        int code;
        String msg;
        JobInfo jobInfo = auditDao.getAudit(audit);
        if(jobInfo != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(jobInfo);
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
     * 修改一条工作审核进度
     * @param jobId
     * @param req
     * @return
     */
    public Object UpdateAudit(HttpServletRequest req, int jobId){
        int code = 500;
        String msg = "内部异常";
        try{
            Audit audit = new Audit();
            String token = req.getHeader("token");
            JsonUtil userJon = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
            //TODO 根据用户的部门来生成认证的流程
            String workPlace = userJon.getStringOrElse("workPlace");
            if("镇".equalsIgnoreCase(workPlace)){
                audit.setTownAuth(true);
                //TODO 向县级管理人员发送审核钉钉信息
                DingUtil.getInstance().sendDDMessage("","");
            }else if("县".equalsIgnoreCase(workPlace)){
                audit.setCountyAuth(true);
                //TODO 向市级管理人员发送审核钉钉信息
                DingUtil.getInstance().sendDDMessage("","");
            }else{
                audit.setCityAuth(true);
            }
            int flag = auditDao.UpdateAudit(audit);
            if(flag == 1){
                code = 200;
                msg = "成功更新审核";
            }else {
                code = 500;
                msg = "更新用户失败";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return  respEntity;
    }
}
