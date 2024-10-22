package com.dexBusman.controller;

import com.dexBusman.service.BonusService;
import domain.bonusOrderPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qo.BonusorderWareBonusPojo;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-13-时间:17:13
 * @message:
 */
@RequestMapping("/bonus")
@CrossOrigin
@RestController
public class BonusController {

    @Autowired
    private BonusService bonusService;

    /*商家获取尚未参与直播带货秒杀的商品*/
    @PostMapping("/getNoBonusWare")
    JsonObject getNoBonusWare(int busman_id){
        JsonObject jsonObject = new JsonObject();
        List<warePojo> noBonusWare = bonusService.getNoBonusWare(busman_id);

        if (noBonusWare.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(noBonusWare.size());
            jsonObject.setObject(noBonusWare);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*商品参与直播带货信息*/
    @PostMapping("/wareAttendBonus")
    JsonObject wareAttendBonus(warePojo ware){
        JsonObject jsonObject = new JsonObject();

        if (bonusService.wareAttendBonus(ware) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*商人获取已经参与活动的商品*/
    @PostMapping("/getAttendBonusWare")
    JsonObject getAttendBonusWare(int busman_id){
        JsonObject jsonObject = new JsonObject();
        List<warePojo> attendBonusWare = bonusService.getAttendBonusWare(busman_id);

        if (attendBonusWare.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(attendBonusWare.size());
            jsonObject.setObject(attendBonusWare);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*商人修改参加的商品信息*/
    @PostMapping("/UpdateAttendBonusMessage")
    JsonObject UpdateAttendBonusMessage(warePojo ware){
        JsonObject jsonObject = new JsonObject();

        if (bonusService.UpdateAttendBonusMessage(ware) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*商品退出直播带货活动*/
    @PostMapping("/wareOutBonus")
    JsonObject wareOutBonus(int ware_id,int busman_id){
        JsonObject jsonObject = new JsonObject();

        if (bonusService.wareOutBonus(ware_id,busman_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*========================秒杀的订单信息=======================*/
    /*返回商人的全部秒杀订单*/
    @PostMapping("/getAllBonusOrder")
    JsonObject getAllBonusOrder(int busman_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<BonusorderWareBonusPojo> allBonusOrder = bonusService.getAllBonusOrder(busman_id);

        if (allBonusOrder.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(allBonusOrder.size());
            jsonObject.setObject(allBonusOrder);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*商人发出秒杀商品*/
    @PostMapping("/sendWareByBusman")
    JsonObject sendWareByBusman(int bonusOrder_id,String expressNumber){
        JsonObject jsonObject = new JsonObject();

        if (bonusService.sendWareByBusman(bonusOrder_id,expressNumber) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
