package com.project.dao;

import com.project.model.House;

import java.util.List;

/**
 * Created by laishun on 2018/3/9.
 */
public interface HouseDao {
    /**
     * 获取house列表
     * @return
     */
    List<House> getHousesFromLandlord(int userId);

    /**
     * 获取house列表
     * @return
     */
    List<House> getHousesFromTenant(int userId);

    /**
     * 获取house列表
     * @return
     */
    List<House> getHousesFromManager();

    /**
     * create a new house
     * @param house
     * @return
     */
    int createHouse(House house);

    /**
     * delete user
     * @param houseId
     * @return
     */
    int deleteHouse(int houseId);

    /**
     * get house by id
     * @param houseId
     * @return
     */
    House getHouse(int houseId);

    /**
     * get house by id
     * @param house
     * @return
     */
    int updateHouse(House house);

    /**
     * 房东预收租金和实际租金信息
     * @param landlordId
     * @return
     */
    double totalRents(int landlordId);
}
