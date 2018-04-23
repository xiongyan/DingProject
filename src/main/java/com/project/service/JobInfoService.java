package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface JobInfoService {
    /**
     * 获取所有工作信息列表
     * @return
     */
    Object getJobInfos();

    /**
     * create 所有工作信息
     * @param req
     * @return
     */
    Object createJobInfo(HttpServletRequest req);

    /**
     * delete 所有工作信息
     * @param jobId
     * @return
     */
    Object deleteJobInfo(int jobId);

    /**
     * get 所有工作信息 by id
     * @param jobId
     * @return
     */
    Object getJobInfo(HttpServletRequest req,int jobId);

    /**
     * get 所有工作信息 by id
     * @param jobId
     * @param req
     * @return
     */
    Object UpdateJobInfo(HttpServletRequest req, int jobId);


}

