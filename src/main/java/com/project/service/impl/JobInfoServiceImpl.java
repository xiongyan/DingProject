package com.project.service.impl;
import com.project.dao.JobInfoDao;
import com.project.dao.LearnDao;
import com.project.model.RespEntity;
import com.project.model.JobInfo;
import com.project.service.JobInfoService;
import com.project.util.CacheUtil;
import com.project.util.DateUtil;
import com.project.util.JsonUtil;
import com.project.util.RequestUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("jobInfoService")
public class JobInfoServiceImpl implements JobInfoService {

    @Resource
    private JobInfoDao jobInfoDao;

    @Resource
    private LearnDao learnDao;

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RespEntity respEntity ;

    /**
     * 获取所有工作信息列表
     * @return
     */
    @Override
    public Object getJobInfos() {
        List<JobInfo> list = jobInfoDao.getJobInfos();
        int code = 500;
        String msg = "内部发生异常";
        if(list != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(list);
        }else{
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * create 所有工作信息
     * @param req
     * @return
     */
    @Override
    public Object createJobInfo(HttpServletRequest req){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String title = res.getStringOrElse("title");
        String content = res.getStringOrElse("content");
        String token = req.getHeader("token");
        JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
        String author = jsonUtil.getStringOrElse("name");
        String userId = jsonUtil.getStringOrElse("userid");
        int code = 500;
        String msg = "内部异常";
        JobInfo jobInfo = new JobInfo();
        jobInfo.setAuthor(author);
        jobInfo.setTitle(title);
        jobInfo.setContent(content);
        jobInfo.setUserId(userId);
        jobInfo.setTime(DateUtil.getTodayTime());
        //保存新用户
        int flag = jobInfoDao.createJobInfo(jobInfo);
        if(flag == 1){
            code = 200;
            msg = "增加新的工作信息成功";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * delete工作信息
     * @param jobId
     * @return
     */
    public Object deleteJobInfo(int jobId){
        int code = 500;
        String msg = "删除失败，由于内部原因";
        int res = jobInfoDao.deleteJobInfo(jobId);
        if(res == 1){
            code = 200;
            msg = "删除成功";
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return  respEntity;
    }

    /**
     * get 所有工作信息 by id
     * @param jobId
     * @return
     */
    public Object getJobInfo(HttpServletRequest req,int jobId){
        int code;
        String msg;
        JobInfo jobInfo = jobInfoDao.getJobInfo(jobId);
        //获取用户的学习进度
        String  token = req.getHeader("token");
        JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("userId",jsonUtil.getStringOrElse("userid"));
        parameter.put("jobInfo",jobId);
        Map<String,Object> obj = learnDao.getLearnProgress(parameter);
        if(jobInfo != null){
            code = 200;
            msg = "查询成功";
            if(obj != null){
                jobInfo.setProgress((Double) obj.get("progress"));
            }else {
                jobInfo.setProgress(0.0);
            }
            respEntity.setData(jobInfo);
        }else{
            code = 500;
            msg = "找不到该数据";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 所有工作信息
     * @param jobId
     * @param req
     * @return
     */
    public Object UpdateJobInfo(HttpServletRequest req, int jobId){
        JSONObject jsonObject = requestUtil.getBody(req);
        JsonUtil res = new JsonUtil(jsonObject);
        String title = res.getStringOrElse("title");
        String content = res.getStringOrElse("content");
        int code;
        String msg;
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(jobId);
        jobInfo.setTitle(title);
        jobInfo.setContent(content);
        int flag = jobInfoDao.UpdateJobInfo(jobInfo);
        if(flag == 1){
            code = 200;
            msg = "更新用户成功";
            respEntity.setData(jobInfo);
        }else {
            code = 500;
            msg = "更新用户失败";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 模糊查询工作信息
     * @return
     */
    public Object queryJobInfo(String subject){
        List<JobInfo> list = jobInfoDao.queryJobInfo("%"+subject+"%");
        int code;
        String msg;
        if(list != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(list);
        }else{
            code = 200;
            msg = "数据为空";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }
}
