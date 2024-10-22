package com.dexUser.controller;

import com.dexUser.service.UserService;
import domain.userPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:50
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*用户更改自己的信息*/
    @RequestMapping("/updateUserInfo")
    JsonObject updateUserInfo(userPojo user){
        return userService.updateUserInfo(user);
    }


    /*修改密码*/
    @RequestMapping("/submitPass")
    JsonObject submitPass(int user_id,String password){
        userPojo user = new userPojo();
        user.setId(user_id);
        user.setPassword(password);
        return userService.submitPass(user);
    }

    /*更改身份证*/
    @RequestMapping("/submitIdCard")
    JsonObject submitIdCard(userPojo user){
        return userService.submitIdCard(user);
    }

    /*更改邮箱*/
    @RequestMapping("/submitEmail")
    JsonObject submitEmail(userPojo user){
        return userService.submitEmail(user);
    }

    /*更改手机号*/
    @RequestMapping("/submitPhone")
    JsonObject submitPhone(userPojo user){

        return userService.submitPhone(user);
    }


    /*获取用户信息*/
    @PostMapping("/getUserMessage")
    JsonObject getUserMessage(int user_id){
        JsonObject jsonObject = new JsonObject();
        userPojo userMessage = userService.getUserMessage(user_id);

        if (userMessage == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(userMessage);
        }

        return jsonObject;
    }
}
