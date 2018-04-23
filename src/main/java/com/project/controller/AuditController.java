package com.project.controller;

import com.project.service.AuditService;
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
     * 查询所有工作审核
     * @return
     */
    @RequestMapping(value="/audits",method= RequestMethod.GET)
    public Object getAudits() {
        return auditService.getAudits();
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
     * @param audit
     * @return
     */
    @RequestMapping(value="/audits/{audit}",method= RequestMethod.POST)
    public Object UpdateAudit(HttpServletRequest req,@PathVariable int audit){
        return auditService.UpdateAudit(req, audit);
    }
}
