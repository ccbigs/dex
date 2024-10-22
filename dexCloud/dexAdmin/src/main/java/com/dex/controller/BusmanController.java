package com.dex.controller;

import com.dex.service.BusmanService;
import domain.busmanPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:26
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class BusmanController {

    @Autowired
    private BusmanService busmanService;

    /*返回全部的商家信息*/
    @RequestMapping("/adminGetAllBusman")
    JsonObject adminGetAllBusman() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<busmanPojo> busmans = busmanService.adminGetAllBusman();

        if (busmans.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(busmans.size());
            jsonObject.setObject(busmans);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    };

    /*管理员更新商家的状态*/
    @RequestMapping("/EditBusmanState")
    JsonObject EditBusmanState(int id, String busman_state){
        JsonObject jsonObject = new JsonObject();

        if (busmanService.EditBusmanState(id, busman_state) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
