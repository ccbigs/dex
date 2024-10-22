package com.dexBusman.dao;

import domain.busmanPojo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:16
 * @message:
 */
public interface LoginRegisterDao {

    /*商家登录*/
    busmanPojo busmanLogin(String account);

    /*商家注册*/
    int busmanRegister(busmanPojo busman);

    /*更新商家上次登录时间*/
    void updateBusmanLoginTime(busmanPojo busman);

    /*判断商家的手机号是否已经注册了*/
    @Select("select * from busman where bus_phone=#{phone}")
    busmanPojo busmanExit(String phone);
}
