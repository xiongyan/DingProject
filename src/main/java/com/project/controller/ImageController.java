package com.project.controller;

import com.project.service.ImageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/15.
 */
@RestController
public class ImageController {

    @Resource
    private ImageService imageService;

    @RequestMapping(value="/imageUpload",method= RequestMethod.POST)
    public Object imageUpload(HttpServletRequest request) {
        return imageService.imageUpload(request);
    }
}
