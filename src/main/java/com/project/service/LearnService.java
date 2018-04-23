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


}

