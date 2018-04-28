package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface AuditService {
    /**
     * 查询用户创建的所有工作审核
     * @return
     */
    Object getAuditsByCreate(String userId,String type);

    /**
     * 获取审批人员需要进行审批的工作
     * @return
     */
    Object getAuditsByCheck(String userId,String type);

    /**
     * create 所有一条工作审核
     * @param req
     * @return
     */
    Object createAudit(HttpServletRequest req);

    /**
     * delete 所有一条工作审核
     * @param audit
     * @return
     */
    Object deleteAudit(int audit);

    /**
     * 查询一条工作审核
     * @param audit
     * @return
     */
    Object getAudit(int audit);

    /**
     * 修改一条工作审核进度
     * @param req
     * @return
     */
    Object UpdateAudit(HttpServletRequest req);


}

