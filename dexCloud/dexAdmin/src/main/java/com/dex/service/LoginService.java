package com.dex.service;

import utils.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:14:37
 * @message:
 */
public interface LoginService {
    /*--------------管理员登陆----------------*/
    JsonObject adminLogin(HttpServletRequest req,String account, String password);
}
