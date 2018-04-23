package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface AuditService {
    /**
     * 获取所有工作审核
     * @return
     */
    Object getAudits();

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
     * @param jobId
     * @param req
     * @return
     */
    Object UpdateAudit(HttpServletRequest req, int jobId);


}

