package com.dexUser.controller;

import com.alibaba.fastjson.JSON;
import com.dexUser.service.LoginRegisterService;
import domain.phimgPojo;
import domain.userPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:10:44
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class LoginRegisterController {

    @Autowired
    private LoginRegisterService loginRegisterService;

    /*用户登录*/
    @PostMapping("/login")
    JsonObject login(String account, String password,HttpServletRequest req){

        return loginRegisterService.login(account, password ,req);
    }

    /*接收前端传过来的用户信息*/
    @RequestMapping("/register")
    JsonObject register(userPojo user){
        user.setAccount(user.getPhone());
        return loginRegisterService.register(user);
    }

    /*用户退出页面，清除后端session*/
    @PostMapping("/loginOut")
    void loginOut(int user_id, HttpServletRequest req){
        req.getSession().removeAttribute(String.valueOf(user_id));
    }




}
