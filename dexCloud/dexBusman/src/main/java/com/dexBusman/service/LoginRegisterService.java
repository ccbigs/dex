package com.dexBusman.service;

import domain.busmanPojo;
import org.apache.ibatis.annotations.Select;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:16
 * @message:
 */
public interface LoginRegisterService {

    /*商家登录*/
    JsonObject busmanLogin(String account, String password);

    /*商家注册*/
    JsonObject busmanRegister(busmanPojo busman);
}
