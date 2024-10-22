package com.dexBusman.controller;

import com.dexBusman.service.BusmanService;
import domain.busmanPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-10-13-时间:11:36
 * @message:
 */
@RequestMapping("/busman")
@CrossOrigin
@RestController
public class BusmanController {

    @Autowired
    private BusmanService busmanService;

    /*更改地址信息*/
    @PostMapping("/updateLocal")
    JsonObject updateLocal(busmanPojo busman){
        JsonObject jsonObject = new JsonObject();

        if (busmanService.updateLocal(busman) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*商家更改登陆密码*/
    @PostMapping("/updatePassword")
    JsonObject updatePassword(int id,String newPass){
        JsonObject jsonObject = new JsonObject();

        if (busmanService.updatePassword(id, newPass) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
