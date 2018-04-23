package com.project.controller;

import com.project.service.JobInfoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class JobInfoController {

    @Resource
    private JobInfoService jobInfoService;

    /**
     * 查询所有工作信息
     * @return
     */
    @RequestMapping(value="/jobInfo",method= RequestMethod.GET)
    public Object getJobInfos() {
        return jobInfoService.getJobInfos();
    }

    /**
     * 创建工作信息
     * @param req
     * @return
     */
    @RequestMapping(value="/jobInfo",method= RequestMethod.POST)
    public Object createJobInfo(HttpServletRequest req){
        return jobInfoService.createJobInfo(req);
    }

    /**
     * 删除工作信息
     * @param jobId
     * @return
     */
    @RequestMapping(value="/jobInfo/{jobId}",method= RequestMethod.DELETE)
    public Object deleteJobInfo(@PathVariable int jobId){
        return jobInfoService.deleteJobInfo(jobId);
    }

    /**
     * 查询工作信息
     * @param jobId
     * @return
     */
    @RequestMapping(value="/jobInfo/{jobId}",method= RequestMethod.GET)
    public Object getJobInfo(HttpServletRequest req, @PathVariable int jobId){
        return jobInfoService.getJobInfo(req,jobId);
    }

    /**
     * 修改工作信息
     * @param req
     * @param jobId
     * @return
     */
    @RequestMapping(value="/jobInfo/{jobId}",method= RequestMethod.POST)
    public Object UpdateJobInfo(HttpServletRequest req,@PathVariable int jobId){
        return jobInfoService.UpdateJobInfo(req, jobId);
    }
}
