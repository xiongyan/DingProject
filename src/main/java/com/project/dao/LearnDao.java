package com.project.dao;


import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface LearnDao {

    /**
     * 增加一条学习进度
     * @param parameter
     * @return
     */
    int createLearnProgress(Map<String,Object> parameter);

   /**
     * 查询当前用户当前文件或视频学习进度
     * @param parameter
     * @return
     */
    Map<String,Object> getLearnProgress(Map<String,Object> parameter);

    /**
     * 修改当前用户对当前文件或视频的学习进度
     * @param parameter
     * @return
     */
    int updateLearnProgress(Map<String,Object> parameter);

}
