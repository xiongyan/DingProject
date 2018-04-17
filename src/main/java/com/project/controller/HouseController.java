package com.project.controller;

import com.project.service.HouseService;
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
public class HouseController {

    @Resource
    private HouseService houseService;

    @RequestMapping(value="/houses",method= RequestMethod.GET)
    public Object getHouses(HttpServletRequest req) {
        return houseService.getHouses(req);
    }

    @RequestMapping(value="/houses",method= RequestMethod.POST)
    public Object createHouse(HttpServletRequest req){
        return houseService.createHouse(req);
    }

    @RequestMapping(value="/houses/{houseId}",method= RequestMethod.DELETE)
    public Object deleteHouse(@PathVariable String houseId){
        return houseService.deleteHouse(houseId);
    }

    @RequestMapping(value="/houses/{houseId}",method= RequestMethod.GET)
    public Object getHouse(@PathVariable String houseId){
        return houseService.getHouse(houseId);
    }

    @RequestMapping(value="/houses/{houseId}",method= RequestMethod.POST)
    public Object UpdateHouse(HttpServletRequest req,@PathVariable String houseId){
        return houseService.UpdateHouse(req, houseId);
    }

}
