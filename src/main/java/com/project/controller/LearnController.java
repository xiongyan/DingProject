package com.project.controller;

import com.project.service.LearnService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class LearnController {

    @Resource
    private LearnService learnService;

    /**
     * 增加一条学习进度
     * @param req
     * @return
     */
    @RequestMapping(value="/progress",method= RequestMethod.POST)
    public Object createLearnProgress(HttpServletRequest req){
        return learnService.createLearnProgress(req);
    }

    /**
     * 修改当前用户对当前文件或视频的学习进度
     * @param req
     * @return
     */
    @RequestMapping(value="/progress/update",method= RequestMethod.POST)
    public Object UpdateULearnProgress(HttpServletRequest req){
        return learnService.UpdateULearnProgress(req);
    }
}
