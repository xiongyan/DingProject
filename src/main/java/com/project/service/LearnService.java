package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface LearnService {
    /**
     * 增加一条学习进度
     * @param req
     * @return
     */
    Object createLearnProgress(HttpServletRequest req);

    /**
     * 修改当前用户对当前文件或视频的学习进度
     * @param req
     * @return
     */
    Object UpdateULearnProgress(HttpServletRequest req);

    /**
     * 查看用户正在学习的
     * @param userId
     * @return
     */
    Object unfinishedLearn(int userId);

    /**
     * 查看用户已经正在学习的
     * @param userId
     * @return
     */
    Object finishedLearn(int userId);


}

