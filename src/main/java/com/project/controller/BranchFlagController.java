package com.project.controller;

import com.project.service.BranchFlagService;
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
public class BranchFlagController {

    @Resource
    private BranchFlagService branchFlagService;

    /**
     * 获取每个支部的详情信息列表
     * @return
     */
    @RequestMapping(value="/branch_flags",method= RequestMethod.GET)
    public Object getBranchFlags() {
        return branchFlagService.getBranchFlags();
    }

    /**
     * 插入每个支部的详情信息列表
     * @param req
     * @return
     */
    @RequestMapping(value="/branch_flags",method= RequestMethod.POST)
    public Object createBranchFlag(HttpServletRequest req){
        return branchFlagService.createBranchFlag(req);
    }

    /**
     * 删除支部的详情
     * @param branchId
     * @return
     */
    @RequestMapping(value="/branch_flags/{branchId}",method= RequestMethod.DELETE)
    public Object deleteBranch(@PathVariable int branchId){
        return branchFlagService.deleteBranch(branchId);
    }

    /**
     * 查询单个支部
     * @param branchId
     * @return
     */
    @RequestMapping(value="/branch_flags/{branchId}",method= RequestMethod.GET)
    public Object getBranch(@PathVariable int branchId){
        return branchFlagService.getBranch(branchId);
    }

    /**
     * 修改支部信息
     * @param req
     * @param branchId
     * @return
     */
    @RequestMapping(value="/branch_flags/{branchId}",method= RequestMethod.POST)
    public Object UpdateBranch(HttpServletRequest req,@PathVariable int branchId){
        return branchFlagService.UpdateBranch(req, branchId);
    }

}
