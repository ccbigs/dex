package com.dexWare.controller;

import com.dexWare.service.DetailWareService;
import domain.activePojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:25
 * @message:
 */
@RestController
@RequestMapping("/wares")
@CrossOrigin
public class DetailWareController {

    @Autowired
    private DetailWareService detailWareService;

    /*返回单个商品页面*/
    @RequestMapping("/getWareById")
    public JsonObject getWareById(int ware_id){
        JsonObject wareJson = new JsonObject();
        warePojo ware = detailWareService.getWareById(ware_id);
        wareJson.setObject(ware);
        wareJson.setCode(200);
        wareJson.setSize(1);

        return wareJson;
    }

    /*在详情页面购买商品后减少商品的数量*/
    @RequestMapping("/reduceWareForWareDetail")
    JsonObject reduceWareForWareDetail(int ware_id,int number){
        return detailWareService.reduceWareForWareDetail(ware_id, number);
    }

    /*用户点击商品详情页面，返回该商品的打折信息*/
    @RequestMapping("/getWareActive")
    JsonObject getWareActive(int ware_id){
        JsonObject jsonObject = new JsonObject();

        activePojo wareActive = detailWareService.getWareActive(ware_id);

        if (wareActive == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(1);
            jsonObject.setObject(wareActive);
        }

        return jsonObject;
    }

    /*返回商品的发货地址信息*/
    @PostMapping("/getBusmanAddress")
    JsonObject getBusmanAddress(int ware_id){
        JsonObject jsonObject = new JsonObject();

        jsonObject.setObject(detailWareService.getBusmanAddress(ware_id));

        return jsonObject;
    }
}
