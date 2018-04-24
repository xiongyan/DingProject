package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface FileService {

    /**
     * 上传图片
     * @param request
     * @return
     */
    Object fileUpload(HttpServletRequest request);
}
