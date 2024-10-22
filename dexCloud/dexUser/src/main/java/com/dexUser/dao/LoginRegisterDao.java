package com.dexUser.dao;

import domain.userPojo;
import org.apache.ibatis.annotations.Param;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:10:46
 * @message:
 */
public interface LoginRegisterDao {

    userPojo login(String account);

    /*注册*/
    int register(userPojo user);

    /*注册时判断用户的手机号码是否已经注册*/
    int phoneHas(@Param("phone") String phone);


}
