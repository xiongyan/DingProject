package com.project.dao;

import com.project.model.Branch;
import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
public interface BranchFlagDao {
    /**
     * 获取每个支部的详情信息列表
     * @return
     */
    List<Branch> getBranchFlags();

    /**
     * 创建支部的详情信息
     * @param branch
     * @return
     */
    int createBranchFlag(Branch branch);

    /**
     * delete 支部的详情
     * @param branchId
     * @return
     */
    int deleteBranch(int branchId);

    /**
     * 获取单个支部
     * @param branchId
     * @return
     */
    Branch getBranch(int branchId);

    /**
     * 更新支部信息
     * @param branch
     * @return
     */
    int UpdateBranch(Branch branch);

}
