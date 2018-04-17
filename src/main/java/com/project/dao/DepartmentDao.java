package com.project.dao;

import com.project.model.Department;

/**
 * Created by laishun on 2018/3/9.
 */
public interface DepartmentDao {

    /**
     * 初始化
     * @param department
     * @return
     */
    int createDepartment(Department department);

    /**
     * 充值账户
     * @return
     */
    Department getDepartment(String title);


}
