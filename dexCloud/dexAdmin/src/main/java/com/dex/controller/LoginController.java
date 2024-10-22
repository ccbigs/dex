package com.dex.controller;

import com.dex.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:14:31
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /*------------------------管理员登陆-------------------------*/
    @PostMapping("/adminLogin")
    JsonObject adminLogin(HttpServletRequest req,String account, String password){
        return loginService.adminLogin(req,account, password);
    }
}
