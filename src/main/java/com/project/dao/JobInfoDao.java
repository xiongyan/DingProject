package com.project.dao;

import com.project.model.JobInfo;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
public interface JobInfoDao {
    /**
     * 获取用户列表
     * @return
     */
    List<JobInfo> getJobInfos();

    /**
     * create a new user
     * @param jobInfo
     * @return
     */
    int createJobInfo(JobInfo jobInfo);

    /**
     * delete user
     * @param jobId
     * @return
     */
    int deleteJobInfo(int jobId);

    /**
     * if user exit by id
     * @param jobId
     * @return
     */
    JobInfo getJobInfo(int jobId);

    /**
     * delete user
     * @param jobInfo
     * @return
     */
    int UpdateJobInfo(JobInfo jobInfo);

    /**
     * 模糊查询工作信息
     * @return
     */
    List<JobInfo> queryJobInfo(String subject);
}
