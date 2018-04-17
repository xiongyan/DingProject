package com.project.controller;

import com.project.service.DingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by laishun on 2018/3/9.
 */
@RestController
public class DingController {

    @Resource
    private DingService dingService;

    /**
     * 获取第三方应用
     * @return
     */
    @RequestMapping(value="/appList",method= RequestMethod.GET)
    public Object getThirdAppList() {
        return dingService.getThirdAppList();
    }

}
