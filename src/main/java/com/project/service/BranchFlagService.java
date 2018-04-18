package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface BranchFlagService {
    /**
     * 获取每个支部的详情信息列表
     * @return
     */
    Object getBranchFlags();

    /**
     * create 支部的详情信息
     * @param req
     * @return
     */
    Object createBranchFlag(HttpServletRequest req);


    /**
     * delete 支部的详情信息
     * @param branchId
     * @return
     */
    Object deleteBranch(int branchId);

    /**
     * get house by id
     * @param branchId
     * @return
     */
    Object getBranch(int branchId);

    /**
     * 修改支部的详情信息
     * @param branchId
     * @param req
     * @return
     */
    Object UpdateBranch(HttpServletRequest req, int branchId);

}

