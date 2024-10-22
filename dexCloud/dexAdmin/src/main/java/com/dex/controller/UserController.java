package com.dex.controller;

import com.dex.service.UserService;
import domain.userPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:36
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class UserController {

    @Autowired
    private UserService userService;


    /*管理员获得全部的用户信息*/
    @RequestMapping("/adminGetAllUser")
    JsonObject adminGetAllUser(){
        JsonObject jsonObject = new JsonObject();
        List<userPojo> userPojos = userService.adminGetAllUser();

        if (userPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(userPojos.size());
            jsonObject.setObject(userPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员修改用户状态*/
    @RequestMapping("/adminModifyUserState")
    JsonObject adminModifyUserState(int id,String state){
        JsonObject jsonObject = new JsonObject();

        if (userService.adminModifyUserState(id, state) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    /*管理员删除指定用户*/
    @RequestMapping("/adminDeleteUser")
    JsonObject adminDeleteUser(int id){
        JsonObject jsonObject = new JsonObject();

        if (userService.adminDeleteUser(id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }
}
