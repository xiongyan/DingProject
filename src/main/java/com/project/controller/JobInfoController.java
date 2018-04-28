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
     * 查询所有优秀党建新闻
     * @return
     */
    @RequestMapping(value="/fine_news",method= RequestMethod.GET)
    public Object getFineNews() {
        return jobInfoService.getNews("fine");
    }

    /**
     * 查询所有党建新闻
     * @return
     */
    @RequestMapping(value="/news",method= RequestMethod.GET)
    public Object getNews() {
        return jobInfoService.getNews("normal");
    }

    /**
     * 创建党建新闻
     * @param req
     * @return
     */
    @RequestMapping(value="/news",method= RequestMethod.POST)
    public Object createNew(HttpServletRequest req){
        return jobInfoService.createJobInfo(req,"new");
    }

    /**
     * 删除党建新闻
     * @param jobId
     * @return
     */
    @RequestMapping(value="/news/{jobId}",method= RequestMethod.DELETE)
    public Object deleteNew(@PathVariable int jobId){
        return jobInfoService.deleteJobInfo(jobId);
    }

    /**
     * 查询党建新闻
     * @param jobId
     * @return
     */
    @RequestMapping(value="/news/{jobId}",method= RequestMethod.GET)
    public Object getNew(HttpServletRequest req, @PathVariable int jobId){
        return jobInfoService.getJobInfo(req,jobId);
    }

    /**
     * 修改党建新闻
     * @param req
     * @param jobId
     * @return
     */
    @RequestMapping(value="/news/{jobId}",method= RequestMethod.POST)
    public Object UpdateNews(HttpServletRequest req,@PathVariable int jobId){
        return jobInfoService.UpdateJobInfo(req, jobId);
    }

    /**
     * 根据新闻ID查询该用户创建的党建新闻
     * @param req
     * @return
     */
    @RequestMapping(value="/news/user",method= RequestMethod.GET)
    public Object getNewByUserId(HttpServletRequest req){
        return jobInfoService.getJobInfoByUserId(req, "new");
    }

    /**
     * 置顶党建新闻
     * @param jobId
     * @return
     */
    @RequestMapping(value="/news/stick/{jobId}",method= RequestMethod.GET)
    public Object stickNew(@PathVariable int jobId){
        return jobInfoService.stickNew(jobId);
    }


    //===========================学习内容======================
    /**
     * 查询所有学习内容
     * @return
     */
    @RequestMapping(value="/learnContent",method= RequestMethod.GET)
    public Object getLearnContents() {
        return jobInfoService.getLearnContents();
    }

    /**
     * 创建学习内容
     * @param req
     * @return
     */
    @RequestMapping(value="/learnContent",method= RequestMethod.POST)
    public Object createLearnContent(HttpServletRequest req){
        return jobInfoService.createJobInfo(req,"learn");
    }

    /**
     * 删除学习内容
     * @param jobId
     * @return
     */
    @RequestMapping(value="/learnContent/{jobId}",method= RequestMethod.DELETE)
    public Object deleteLearnContent(@PathVariable int jobId){
        return jobInfoService.deleteJobInfo(jobId);
    }

    /**
     * 查询学习内容
     * @param jobId
     * @return
     */
    @RequestMapping(value="/learnContent/{jobId}",method= RequestMethod.GET)
    public Object getLearnContent(HttpServletRequest req, @PathVariable int jobId){
        return jobInfoService.getJobInfo(req,jobId);
    }

    /**
     * 修改学习内容
     * @param req
     * @param jobId
     * @return
     */
    @RequestMapping(value="/learnContent/{jobId}",method= RequestMethod.POST)
    public Object UpdateLearnContent(HttpServletRequest req,@PathVariable int jobId){
        return jobInfoService.UpdateJobInfo(req, jobId);
    }

    /**
     * 根据新闻ID查询该用户创建的学习内容
     * @param req
     * @return
     */
    @RequestMapping(value="/learnContent/user",method= RequestMethod.GET)
    public Object getLearnContentByUserId(HttpServletRequest req){
        return jobInfoService.getJobInfoByUserId(req, "learn");
    }

    /**
     * 模糊查询工作信息
     * @return
     */
    @RequestMapping(value="/query/{type}",method= RequestMethod.GET)
    public Object queryJobInfo(HttpServletRequest req,@PathVariable String type) {
        String subject = req.getParameter("subject");
        return jobInfoService.queryJobInfo(subject,type);
    }
}
