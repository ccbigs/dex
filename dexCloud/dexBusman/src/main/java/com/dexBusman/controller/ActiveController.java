package com.dexBusman.controller;

import com.dexBusman.service.ActiveService;
import domain.activePojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:37
 * @message:
 */

@RequestMapping("/active")
@CrossOrigin
@RestController
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    /*-----------------------商家后台-----------------------*/
    /*商人查询未参与活动的商品*/
    @RequestMapping("busmanNoActive")
    JsonObject busmanNoActive(int busman_id){
        return activeService.busmanNoActive(busman_id);
    }

    /*商人查询自己已参与活动的商品*/
    @RequestMapping("getActiveDidWare")
    JsonObject getActiveDidWare(int busman_id){
        return activeService.getActiveDidWare(busman_id);
    }

/*===============================*/
    /*商家添加满减活动*/
    @RequestMapping("/busmanAddFullReduction")
    JsonObject busmanAddFullReduction(activePojo active){
        active.setType("满减");
        return activeService.busmanAddFullReduction(active);
    }

    /*商家添加打折活动*/
    @RequestMapping("/busmanAddDiscount")
    JsonObject busmanAddDiscount(activePojo active){
        active.setType("打折");
        return activeService.busmanAddDiscount(active);
    }
/*=====================================================*/
    /*商家退出活动*/
    @RequestMapping("/outActive")
    JsonObject outActive(int busman_id, int ware_id){
        return activeService.outActive(busman_id, ware_id);
    }

    /*商家获取指定的参与活动的信息*/
    @RequestMapping("/getWareActiveMessage")
    JsonObject getWareActiveMessage(int ware_id,int busman_id){
        return activeService.getWareActiveMessage(ware_id, busman_id);
    }

    /*商家修改商品的打折信息*/
    @RequestMapping("/busmanUpdateDiscount")
    JsonObject busmanUpdateDiscount(warePojo active){
        return activeService.busmanUpdateDiscount(active);
    }

    /*商家修改商品的满减信息*/
    @RequestMapping("/busmanUpdateFull")
    JsonObject busmanUpdateFull(warePojo active){
        return activeService.busmanUpdateFull(active);
    }

}
