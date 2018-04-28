package com.project.controller;

import com.project.service.AuditService;
import com.project.util.CacheUtil;
import com.project.util.JsonUtil;
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
public class AuditController {

    @Resource
    private AuditService auditService;

    /**
     * 查询用户创建的所有工作审核
     * @return
     */
    @RequestMapping(value="/audits/create",method= RequestMethod.GET)
    public Object getAuditsByCreate(HttpServletRequest req) {
        String type = req.getParameter("type");
        //获取用户的学习进度
        String  token = req.getHeader("token");
        JsonUtil jsonUtil = new JsonUtil((JSONObject) CacheUtil.getInstance().get(token));
        String userId = jsonUtil.getStringOrElse("userid");
        return auditService.getAuditsByCreate(userId,type);
    }

    /**
     * 获取审批人员需要进行审批的工作
     * @return
     */
    @RequestMapping(value="/audits/check",method= RequestMethod.GET)
    public Object getAuditsByCheck(HttpServletRequest req) {
        String type = req.getParameter("type");
        //获取用户的学习进度
        String  token = req.getHeader("token");
        JsonUtil jsonUtil = new JsonUtil((JSONObject) CacheUtil.getInstance().get(token));
        String userId = jsonUtil.getStringOrElse("userid");
        return auditService.getAuditsByCheck(userId,type);
    }

    /**
     * 创建一条工作审核
     * @param req
     * @return
     */
    @RequestMapping(value="/audits",method= RequestMethod.POST)
    public Object createAudit(HttpServletRequest req){
        return auditService.createAudit(req);
    }

    /**
     * 删除一条工作审核
     * @param audit
     * @return
     */
    @RequestMapping(value="/audits/{audit}",method= RequestMethod.DELETE)
    public Object deleteAudit(@PathVariable int audit){
        return auditService.deleteAudit(audit);
    }

    /**
     * 查询一条工作审核
     * @param audit
     * @return
     */
    @RequestMapping(value="/audits/{audit}",method= RequestMethod.GET)
    public Object getAudit(@PathVariable int audit){
        return auditService.getAudit(audit);
    }

    /**
     * 修改一条工作审核进程
     * @param req
     * @return
     */
    @RequestMapping(value="/audits/check",method= RequestMethod.POST)
    public Object UpdateAudit(HttpServletRequest req){
        return auditService.UpdateAudit(req);
    }
}
