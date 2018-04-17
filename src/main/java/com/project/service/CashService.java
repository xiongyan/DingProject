package com.project.service;

/**
 * Created by laishun on 2018/3/9.
 */
public interface CashService {
    /**
     * 充值账户
     * @return
     */
    String chargeMoney(int userId, double money);

    /**
     * 提现
     * @param userId
     * @return
     */
    String drawMoney(int userId, double money);

    /**
     * 记录交易日志
     * @return
     */
    String transactions(int userId, double money, String flag, String remark);

    /**
     * 查看账户余额
     * @param userId
     * @return
     */
    Object queryAccount(int userId);

    /**
     * 交房租
     * @param userId
     * @param houseId
     * @return
     */
    Object payRent(int userId, int houseId, double money);

    /**
     * 水费查询
     * @param houseId
     * @return
     */
    Object waterQuery(int houseId);

    /**
     * 电费查询
     * @param houseId
     * @return
     */
    Object electricQuery(int houseId);

}

