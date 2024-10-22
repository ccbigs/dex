package com.dexUser.service;

import domain.userPojo;
import utils.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:10:46
 * @message:
 */
public interface LoginRegisterService {

    /*用户登录*/
    JsonObject login(String account, String password, HttpServletRequest req);

    /*注册*/
    JsonObject register(userPojo user);


}
