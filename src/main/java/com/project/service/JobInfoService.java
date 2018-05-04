package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface JobInfoService {
    /**
     * 获取所有党建新闻信息列表
     * @return
     */
    Object getNews(String flag);

    /**
     * create 所有工作信息
     * @param req
     * @return
     */
    Object createJobInfo(HttpServletRequest req,String type);

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

    /**
     * 根据新闻ID查询该用户创建的党建新闻
     * @param type
     * @return
     */
    Object getJobInfoByUserId(HttpServletRequest req, String type);

    /**
     * 模糊查询工作信息
     * @return
     */
    Object queryJobInfo(String subject,String type);

    /**
     * 查询所有学习内容
     * @return
     */
    Object getLearnContents(String subject);

    /**
     * 置顶党建新闻
     * @param jobId
     * @return
     */
    Object stickNew(int jobId);

}

