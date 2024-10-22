package com.dexBusman.controller;

import com.dexBusman.service.WaresService;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:37
 * @message:
 */

@RestController
@RequestMapping("/wares")
@CrossOrigin
public class WaresController {

    @Autowired
    private WaresService wareservice;

    /*-----------------商家--------------*/
    /*商家上传商品*/
    @RequestMapping("/busmanAddWare")
    JsonObject busmanAddWare(warePojo ware){
        ware.setWare_number(ware.getWare_totalnumber());
        return wareservice.busmanAddWare(ware);
    }

    /*商家商品查询*/
    @RequestMapping("/getWareByBusman")
    JsonObject getWareByBusman(int busman_id) throws ParseException {
        return wareservice.getWareByBusman(busman_id);
    }

    /*商家更新商品信息*/
    @RequestMapping("/busmanUpdateWareMessage")
    JsonObject busmanUpdateWareMessage(warePojo ware){
        return wareservice.busmanUpdateWareMessage(ware);
    }

    /*商家更新商品的总数量*/
    @RequestMapping("/busmanUpdateWareNumber")
    JsonObject busmanUpdateWareNumber(int id,int number){
        return wareservice.busmanUpdateWareNumber(id,number);
    }

    /*更新商家下架商品*/
    @RequestMapping("/busmanUnderCarriageWare")
    JsonObject busmanUnderCarriageWare(int id){
        return wareservice.busmanUnderCarriageWare(id);
    }

    /*更新商家上架商品*/
    @RequestMapping("/busmanGroundWare")
    JsonObject busmanGroundWare(int id){
        return wareservice.busmanGroundWare(id);
    }

    /*获取指定商家售卖中的商品*/
    @RequestMapping("/busmanAlreadyGroundWare")
    JsonObject busmanAlreadyGroundWare(int busman_id){
        return wareservice.busmanAlreadyGroundWare(busman_id);
    }

    /*商家删除指定的物品*/
    @PostMapping("/deleteWare")
    JsonObject busmanDeleteWare(int busmanId,int wareId){
        JsonObject jsonObject = new JsonObject();

        if (wareservice.busmanDeleteWare(busmanId, wareId) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
