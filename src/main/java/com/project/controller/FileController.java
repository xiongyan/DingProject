package com.project.controller;

import com.project.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/15.
 */
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public Object fileUpload(HttpServletRequest request) {
        return fileService.fileUpload(request);
    }
}
