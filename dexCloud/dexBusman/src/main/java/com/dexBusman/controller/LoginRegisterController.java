package com.dexBusman.controller;

import com.dexBusman.service.LoginRegisterService;
import domain.busmanPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;
import utils.getTimeNow;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:16
 * @message:
 */

@RestController
@CrossOrigin
@RequestMapping("/busman")
public class LoginRegisterController {

    @Autowired
    private LoginRegisterService loginRegisterService;

    /*商家登录*/
    @RequestMapping("/busmanLogin")
    JsonObject busmanLogin(String account, String password){
        return loginRegisterService.busmanLogin(account,password);
    }

    /*商家注册*/
    @RequestMapping("/busmanRegister")
    JsonObject busmanRegister(busmanPojo busman){
        busman.setBus_register_time(new getTimeNow().getTimeNowYMD());
        busman.setBus_lasttime(new getTimeNow().getTimeNowYMDHMS());
        return loginRegisterService.busmanRegister(busman);
    }

}
