package com.dex.controller;

import com.dex.service.WareService;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:51
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class WareController {

    @Autowired
    private WareService wareService;

    /*管理员获取全部的商品信息*/
    @RequestMapping("/adminGetAllWare")
    JsonObject adminGetAllWare() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<warePojo> warePojos = wareService.adminGetAllWare();

        if (warePojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(warePojos.size());
            jsonObject.setObject(warePojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员更改商品状态*/
    @RequestMapping("/adminModifyWareState")
    JsonObject adminModifyWareState(int id,String ware_state){
        JsonObject jsonObject = new JsonObject();

        if (wareService.adminModifyWareState(id, ware_state) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    /*管理员删除选中的商品*/
    @RequestMapping("/adminDeleteWare")
    JsonObject adminDeleteWare(int id){
        JsonObject jsonObject = new JsonObject();

        if (wareService.adminDeleteWare(id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
