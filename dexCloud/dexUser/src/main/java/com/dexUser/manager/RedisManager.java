package com.dexUser.manager;

import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:14:17
 * @message:
 */
public interface RedisManager {

    /*存入bonus*/
    void depositBonus(bonusPojo bonus);

    /*存入ware*/
    void depositWare(warePojo ware);

    /*存入standards*/
    void depositStandards(List<standardsPojo> standards,int bonus_id);

    /*存放用户登录信息*/
    void saveUserLoginLog(String UserName);


}
