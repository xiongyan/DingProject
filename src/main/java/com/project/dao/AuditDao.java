package com.project.dao;

import com.project.model.Audit;
import com.project.model.JobInfo;

import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
public interface AuditDao {
    /**
     * 获取工作审核
     * @return
     */
    List<Audit> getAudits();

    /**
     * create 一条工作审核
     * @param audit
     * @return
     */
    int createAudit(Audit audit);

    /**
     * delete 一条工作审核
     * @param audit
     * @return
     */
    int deleteAudit(int audit);

    /**
     * 获取一条工作审核
     * @param audit
     * @return
     */
    JobInfo getAudit(int audit);

    /**
     * 修改一条工作审核
     * @param audit
     * @return
     */
    int UpdateAudit(Audit audit);

    /**
     * 查询是否已经存在这个工作的审核啦
     * @param jobInfoId
     * @return
     */
    Audit findAuditByJobInfoId(int jobInfoId);
}
