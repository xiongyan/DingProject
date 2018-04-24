package com.project.service.impl;

import com.project.dao.LearnDao;
import com.project.model.RespEntity;
import com.project.service.LearnService;
import com.project.util.JsonUtil;
import com.project.util.RequestUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("learnService")
public class LearnServiceImpl implements LearnService {

    @Resource
    private LearnDao learnDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

   /**
     * 增加一条学习进度
     * @param req
     * @return
     */
    @Override
    public Object createLearnProgress(HttpServletRequest req){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String userId = res.getStringOrElse("userId");
        int jobInfo = res.getIntOrElse("jobInfo", -1);
        double progress = res.getDoubbleOrElse("progress", 0.0);
        int code = 500;
        String msg = "创建新的记录失败";
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("userId",userId);
        parameter.put("jobInfo",jobInfo);
        parameter.put("progress",progress);
        Map<String,Object> obj = learnDao.getLearnProgress(parameter);
        if(obj == null){
            int flag = learnDao.createLearnProgress(parameter);
            if(flag == 1){
                code = 200;
                msg = "成功增加用户新的学习进度";
            }
        }else{
            int flag = learnDao.updateLearnProgress(parameter);
            if(flag == 1){
                code = 200;
                msg = "成功修改用户新的学习进度";
            }
        }
        respEntity.setData(null);
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 修改当前用户对当前文件或视频的学习进度
     * @param req
     * @return
     */
    public Object UpdateULearnProgress(HttpServletRequest req){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String userId = res.getStringOrElse("userId");
        int jobInfo = res.getIntOrElse("jobInfo", -1);
        double progress = res.getDoubbleOrElse("progress", 0.0);
        int code;
        String msg;
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("userId", userId);
        parameter.put("jobInfo", jobInfo);
        parameter.put("progress", progress);
        int flag = learnDao.updateLearnProgress(parameter);
        if(flag == 1){
            code = 200;
            msg = "成功修改用户新的学习进度";
        }else {
            code = 500;
            msg = "创建新的记录失败";
        }
        respEntity.setData(null);
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 查看用户正在学习的
     * @param userId
     * @return
     */
    public Object unfinishedLearn(int userId){
        int code = 500;
        String msg = "查询失败";
        respEntity.setData(null);
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 查看用户已经正在学习的
     * @param userId
     * @return
     */
    public Object finishedLearn(int userId){
        int code = 500;
        String msg = "查询失败";
        respEntity.setData(null);
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }
}
