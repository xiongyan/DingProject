package com.project.service.impl;
import com.project.dao.JobInfoDao;
import com.project.dao.LearnDao;
import com.project.model.RespEntity;
import com.project.model.JobInfo;
import com.project.service.JobInfoService;
import com.project.util.*;
import org.json.JSONArray;
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
     * 获取所有党建新闻信息列表
     * @return
     */
    @Override
    public Object getNews(String flag) {
        List<JobInfo> list = jobInfoDao.getNews(flag);
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
    public Object createJobInfo(HttpServletRequest req,String type){
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
        jobInfo.setJobType(type);
        //判断是那个级别的人创建的
        //获取用户的学习进度
        JSONArray array = jsonUtil.getJSONArray("department",null);
        if(array != null && array.length() > 0){
            int department = array.getInt(0);
            JsonUtil depart = new JsonUtil(DingUtil.getInstance().getApartment(department));
            int parentId = depart.getIntOrElse("parentid",-1);
            if(parentId == -1){
                jobInfo.setAudit("true");
            }else {
                jobInfo.setAudit("false");
            }
        }else{
            jobInfo.setAudit("true");
        }
        jobInfo.setQuality("normal");
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
     * get 所有工作信息 by id 并且获取读取时间
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
        String quality = res.getStringOrElse("quality");
        int code;
        String msg;
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(jobId);
        jobInfo.setTitle(title);
        jobInfo.setContent(content);
        jobInfo.setQuality(quality);
        int flag = jobInfoDao.UpdateJobInfo(jobInfo);
        if(flag == 1){
            code = 200;
            msg = "更新数据成功";
            respEntity.setData(jobInfo);
        }else {
            code = 500;
            msg = "更新数据失败";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }

    /**
     * 根据新闻ID查询该用户创建的党建新闻
     * @param type
     * @return
     */
    public Object getJobInfoByUserId(HttpServletRequest req,String type){
        int code;
        String msg;
        //获取用户的学习进度
        String  token = req.getHeader("token");
        JsonUtil jsonUtil = new JsonUtil((JSONObject)CacheUtil.getInstance().get(token));
        JobInfo jobInfo = new JobInfo();
        jobInfo.setUserId(jsonUtil.getStringOrElse("userid"));
        jobInfo.setJobType(type);
        List<JobInfo> list = jobInfoDao.getJobInfoByUserId(jobInfo);
        if(list != null){
            code = 200;
            msg = "查询成功";
            respEntity.setData(list);
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
     * 模糊查询工作信息
     * @return
     */
    public Object queryJobInfo(String subject,String type){
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("subject","%"+subject+"%");
        parameter.put("type",type);
        List<JobInfo> list = jobInfoDao.queryJobInfo(parameter);
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

    /**
     * 查询所有学习内容
     * @return
     */
    public Object getLearnContents() {
        List<JobInfo> list = jobInfoDao.getLearnContents();
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
     * 置顶党建新闻
     * @param jobId
     * @return
     */
    public Object stickNew(int jobId){
        int code;
        String msg;
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(jobId);
        jobInfo.setQuality("fine");
        int flag = jobInfoDao.UpdateJobInfo(jobInfo);
        if(flag == 1){
            code = 200;
            msg = "置顶党建工作成功";
            respEntity.setData(null);
        }else {
            code = 500;
            msg = "置顶党建工作失败";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return  respEntity;
    }
}
