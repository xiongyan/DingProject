package com.project.service;


/**
 * Created by laishun on 2018/3/9.
 */
public interface DingService {
    /**
     * 获取第三方应用
     * @return
     */
    Object getThirdAppList();

    /**
     * 获取获取打卡记录
     * @return
     */
    Object getRecordList(int department);

}

