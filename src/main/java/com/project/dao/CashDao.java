package com.project.dao;

import com.project.model.Account;

import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
public interface CashDao {

    /**
     * 初始化
     * @param userId
     * @return
     */
    int init(int userId);

    /**
     * 充值账户
     * @return
     */
    int chargeMoney(Account account);

    /**
     * 提现
     * @return
     */
    int drawMoney(Account account);

    /**
     *  取出money
     * @param userId
     * @return
     */
    Account getAccount(int userId);

    /**
     * 保存交易日志
     * @param account
     * @return
     */
    int transactions(Account account);

    /**
     * 交房租
     * @param map
     * @return
     */
    int payRent(Map map);


}
