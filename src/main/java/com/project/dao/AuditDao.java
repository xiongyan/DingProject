package com.project.dao;

import com.project.model.Audit;
import com.project.model.JobInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface AuditDao {
    /**
     * 查询用户创建的所有工作审核
     * @return
     */
    List<JobInfo> getNewsAuditsByCreate(String userId);

    /**
     * 查询用户创建的所有工作审核
     * @return
     */
    List<Map<String,Object>> getExamAuditsByCreate(String userId);

    /**
     * 获取审批人员需要进行审批的工作
     * @return
     */
    List<JobInfo> getNewsAuditsByCheck(String userId);

    /**
     * 获取审批人员需要进行审批的工作
     * @return
     */
    List<Map<String,Object>> getExamAuditsByCheck(String userId);

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
    Audit getAudit(int audit);

    /**
     * 修改一条工作审核
     * @param audit
     * @return
     */
    int UpdateAudit(Audit audit);

    /**
     * 查询是否已经存在这个工作的审核啦
     * @param parameter
     * @return
     */
    Audit findAuditByJobInfoId(Map<String,Object> parameter);
}
