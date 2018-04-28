package com.project.service.impl;

import com.project.dao.AuditDao;
import com.project.dao.ExamDao;
import com.project.dao.JobInfoDao;
import com.project.model.Audit;
import com.project.model.JobInfo;
import com.project.model.RespEntity;
import com.project.service.AuditService;
import com.project.util.*;
import org.json.JSONArray;
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
@Service("auditService")
public class AuditServiceImpl implements AuditService {

    @Resource
    private AuditDao auditDao;

    @Resource
    private JobInfoDao jobInfoDao;

    @Resource
    private ExamDao examDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    private String content = "{\"message_url\": \"#\",\"head\": {\"bgcolor\": \"FFBBBBBB\",\"text\": \"审核任务提醒\"},\"body\": {\"title\": \"有审核任务\",\"content\": \"有审核任务，请及时去管理系统处理\",\"author\": \"系统提醒 \"}}";

    /**
     * 查询用户创建的所有工作审核
     * @return
     */
    @Override
    public Object getAuditsByCreate(String userId,String type) {
        int code = 500;
        String msg = "内部发生异常";
        if(type.equalsIgnoreCase("new")){
            List<JobInfo> list = auditDao.getNewsAuditsByCreate(userId);
            if(list != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(list);
            }else{
                respEntity.setData(null);
            }
        }else if(type.equalsIgnoreCase("exam")){
            List<Map<String,Object>> list = auditDao.getExamAuditsByCreate(userId);
            if(list != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(list);
            }else{
                respEntity.setData(null);
            }
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 获取审批人员需要进行审批的工作
     * @return
     */
    @Override
    public Object getAuditsByCheck(String userId,String type) {
        int code = 500;
        String msg = "内部发生异常";
        if(type.equalsIgnoreCase("new")){
            List<JobInfo> list = auditDao.getNewsAuditsByCheck(userId);
            if(list != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(list);
            }else{
                respEntity.setData(null);
            }
        }else if(type.equalsIgnoreCase("exam")){
            List<Map<String,Object>> list = auditDao.getExamAuditsByCheck(userId);
            if(list != null){
                code = 200;
                msg = "查询成功";
                respEntity.setData(list);
            }else{
                respEntity.setData(null);
            }
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
        String msg = "内部异常";
        try{
            JSONObject jsonObject = requestUtil.getBody(req);
            JsonUtil res = new JsonUtil(jsonObject);
            int jobInfoId = res.getIntOrElse("jobInfoId", -1);
            String type = res.getStringOrElse("type");
            Map<String,Object> parameter = new HashMap<>();
            parameter.put("jobInfoId",jobInfoId);
            parameter.put("type",type);
            Audit audit_old = auditDao.findAuditByJobInfoId(parameter);
            if(audit_old == null){
                //create a new audit
                Audit audit = new Audit();
                audit.setJobInfoId(jobInfoId);
                audit.setTime(DateUtil.getTodayTime());
                audit.setIsFinish(false);
                audit.setType(type);
                //判断用户是什么level提交的
                String token = req.getHeader("token");
                JsonUtil userJon = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
                String userId = userJon.getStringOrElse("userid");
                audit.setCreateUser(userId);
                JSONArray array = userJon.getJSONArray("department",null);
                if(array != null && array.length() > 0){
                    int department = array.getInt(0);
                    String manager = getManager(department);
                    if(manager == null){
                        audit.setIsFinish(true);
                        //更新党建新闻和考试审核
                        if("new".equalsIgnoreCase(type)){
                            JobInfo jobInfo = new JobInfo();
                            jobInfo.setId(audit_old.getJobInfoId());
                            jobInfo.setAudit("true");
                            jobInfoDao.UpdateJobInfo(jobInfo);
                        }else if("exam".equalsIgnoreCase(type)){
                            Map<String,Object> exams = new HashMap<>();
                            exams.put("id",audit_old.getJobInfoId());
                            exams.put("audit","true");
                            examDao.UpdateExam(exams);
                        }
                    }else{
                        audit.setAuditUser(manager);
                        //TODO 向镇级管理人员发送审核钉钉信息
                        DingUtil.getInstance().sendDDMessage(manager,content);
                    }
                    //保存新用户
                    int flag = auditDao.createAudit(audit);
                    if(flag == 1){
                        code = 200;
                        msg = "增加新的工作审核成功";
                        respEntity.setData(null);
                    }
                }
            }else{
                code = 500;
                msg = "该工作报告已经正在审核或是已经审核完成啦";
                respEntity.setData(null);
            }
        }catch(Exception e){
            e.printStackTrace();
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 根据部门id获取上一级manager
     * @param department
     * @return
     */
    public String getManager(int department){
        String result = null;
        JsonUtil depart = new JsonUtil(DingUtil.getInstance().getApartment(department));
        int parentId = depart.getIntOrElse("parentid",-1);
        if(parentId != -1){
            JsonUtil parentDepart = new JsonUtil(DingUtil.getInstance().getApartment(parentId));
            String managerList = parentDepart.getStringOrElse("deptManagerUseridList");
            if(!"".equalsIgnoreCase(managerList)){
                if(managerList.contains("|")){
                    result = managerList.split("|")[0];
                }else{
                    result = managerList;
                }
            }else{
                result = parentDepart.getStringOrElse("orgDeptOwner");
            }
        }
        return result;
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
        Audit audit1 = auditDao.getAudit(audit);
        if(audit1 != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(audit1);
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
     * @param req
     * @return
     */
    public Object UpdateAudit(HttpServletRequest req){
        int code = 500;
        String msg = "内部异常";
        try{
            JSONObject jsonObject = requestUtil.getBody(req);
            JsonUtil res = new JsonUtil(jsonObject);
            int jobInfoId = res.getIntOrElse("jobInfoId", -1);
            String type = res.getStringOrElse("type");
            boolean result = res.getBooleanOrElse("result", true);
            if(result == true){
                Map<String,Object> parameter = new HashMap<>();
                parameter.put("jobInfoId",jobInfoId);
                parameter.put("type",type);
                Audit audit_old = auditDao.findAuditByJobInfoId(parameter);
                //判断用户是什么level提交的
                String token = req.getHeader("token");
                JsonUtil userJon = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
                JSONArray array = userJon.getJSONArray("department",null);
                if(array != null && array.length() > 0){
                    int department = array.getInt(0);
                    String manage = getManager(department);
                    if(manage == null){
                        audit_old.setIsFinish(true);
                        auditDao.UpdateAudit(audit_old);
                        //更新党建新闻和考试审核
                        if("new".equalsIgnoreCase(type)){
                            JobInfo jobInfo = new JobInfo();
                            jobInfo.setId(audit_old.getJobInfoId());
                            jobInfo.setAudit("true");
                            jobInfoDao.UpdateJobInfo(jobInfo);
                        }else if("exam".equalsIgnoreCase(type)){
                            Map<String,Object> exams = new HashMap<>();
                            exams.put("id",audit_old.getJobInfoId());
                            exams.put("audit","true");
                            examDao.UpdateExam(exams);
                        }
                    }else{
                        audit_old.setAuditUser(manage);
                        int flag =  auditDao.UpdateAudit(audit_old);
                        if(flag == 1){
                            code = 200;
                            msg = "成功更新审核";
                        }else {
                            code = 500;
                            msg = "更新用户失败";
                        }
                        //TODO 向镇级管理人员发送审核钉钉信息
                        DingUtil.getInstance().sendDDMessage(manage,content);
                    }
                }
            }else{
                code = 200;
                msg = "成功更新审核";
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
