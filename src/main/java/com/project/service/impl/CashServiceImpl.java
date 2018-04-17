package com.project.service.impl;

import com.project.dao.CashDao;
import com.project.dao.HouseDao;
import com.project.model.Account;
import com.project.model.House;
import com.project.model.RespEntity;
import com.project.service.CashService;
import com.project.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by laishun on 2018/3/9.
 */
@Service("cashService")
public class CashServiceImpl implements CashService {

    @Resource
    private CashDao cashDao;

    @Resource
    private RespEntity respEntity;

    @Resource
    private HouseDao houseDao;

    /**
     * queryAccount 查询余额
     * @param userId
     * @return
     */
    @Override
    public Object queryAccount(int userId){
        double account = 0.0;
        Account acnt = cashDao.getAccount(userId);
        if(acnt != null){
            account = acnt.getTotal();
        }
        respEntity.setCode(200);
        respEntity.setMsg("查询成功");
        respEntity.setData(account);
        return respEntity;
    }

    /**
     * 交房租
     * @param userId
     * @param houseId
     * @return
     */
    @Override
    public Object payRent(int userId, int houseId,double money){
        int code = 500;
        String msg = "内部异常";
        Account account = cashDao.getAccount(userId);
        if(account == null){
            cashDao.init(userId);
            msg = "账户金额不足，请先充值在交租金";
        }else{
            if(account.getTotal() < money){
                msg = "账户金额不足，请先充值在交租金";
                code = 500;
            }else{
                Map<String,Object> parameter = new HashMap<>();
                House house = houseDao.getHouse(houseId);
                parameter.put("userId",userId);
                parameter.put("houseId",houseId);
                parameter.put("landlord",house.getLandlord());
                parameter.put("rent",money);
                parameter.put("time", DateUtil.getTodayTime());
                int index = cashDao.payRent(parameter);
                if(index == 1){
                    Account newAccount = new Account();
                    newAccount.setTotal(money);
                    newAccount.setUserId(userId);
                    int flag = cashDao.drawMoney(newAccount);
                    if(flag == 1){
                        code = 200;
                        msg = "交租成功，所交金额为："+money;
                        transactions(userId,money,"payRent","房租缴费");
                    }
                }

            }
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        respEntity.setData(null);
        return respEntity;
    }

    /**
     * 水费查询
     * @param houseId
     * @return
     */
    public Object waterQuery(int houseId){
        return "";
    }

    /**
     * 电费查询
     * @param houseId
     * @return
     */
    public Object electricQuery(int houseId){
        return "";
    }

    /**
     * 充值账户
     * @return
     */
    @Override
    public String chargeMoney(int userId,double money) {
        String msg = "fail";
        Account account = cashDao.getAccount(userId);
        if(account == null){
            int flag = cashDao.init(userId);
            if(flag != 1){
                return msg;
            }
        }
        Account newAccount = new Account();
        newAccount.setTotal(money);
        newAccount.setUserId(userId);
        int index = cashDao.chargeMoney(newAccount);
        if(index == 1){
            msg = "success";
        }
        return msg;
    }

    /**
     * 提现
     * @param userId
     * @return
     */
    @Override
    public String drawMoney(int userId,double money){
        String msg = "fail";
        if(money == 0){
            msg = "fail";
        }else {
            Account account = cashDao.getAccount(userId);
            if(money > account.getTotal()){
                msg = "fail";
            }else {
                Account newAccount = new Account();
                newAccount.setTotal(money);
                newAccount.setUserId(userId);
                int flag = cashDao.drawMoney(newAccount);
                if(flag == 1){
                    msg = "success";
                }
            }
        }
        return  msg;
    }

    /**
     * 记录交易日志
     * @return
     */
    @Override
    public String transactions(int userId,double money,String flag,String remark){
        String msg = "fail";
        Account newAccount = new Account();
        newAccount.setTotal(money);
        newAccount.setUserId(userId);
        newAccount.setFlag(flag);
        newAccount.setTime(DateUtil.getTodayTimeStamp());
        newAccount.setRemark(remark);
        int index = cashDao.transactions(newAccount);
        if(index == 1){
            msg = "success";
        }
        return  msg;
    }

}
