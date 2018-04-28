package com.project.dao;

import com.project.model.JobInfo;
import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface JobInfoDao {
    /**
     * 获取党建新闻列表
     * @return
     */
    List<JobInfo> getNews(String quality);

    /**
     * 查询所有学习内容
     * @return
     */
    List<JobInfo> getLearnContents();

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
    List<JobInfo> queryJobInfo(Map<String,Object> parameter);

    /**
     * 获取党建新闻列表
     * @return
     */
    List<JobInfo> getJobInfoByUserId(JobInfo jobInfo);
}
