package com.project.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by laishun on 2018/3/9.
 */
public interface HouseService {
    /**
     * 获取house列表
     * @return
     */
    Object getHouses(HttpServletRequest req);

    /**
     * create a new user
     * @param req
     * @return
     */
    Object createHouse(HttpServletRequest req);

    /**
     * delete user
     * @param houseId
     * @return
     */
    Object deleteHouse(String houseId);

    /**
     * get house by id
     * @param house_id
     * @return
     */
    Object getHouse(String houseId);

    /**
     * get house by id
     * @param house_id
     * @return
     */
    Object UpdateHouse(HttpServletRequest req, String house_id);


}

