package com.dex.controller;

import com.dex.service.BonusService;
import domain.bonusOrderPojo;
import domain.bonusPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-17-时间:16:56
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    /*管理员查看全部的秒杀信息*/
    @PostMapping("/adminGetAllBonus")
    JsonObject adminGetAllBonus() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<bonusPojo> bonusPojos = bonusService.adminGetAllBonus();

        if (bonusPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setObject(bonusPojos);
            jsonObject.setSize(bonusPojos.size());
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员查看全部的秒杀订单*/
    @PostMapping("/adminGetAllBonusOrder")
    JsonObject adminGetAllBonusOrder() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<bonusOrderPojo> bonusOrderPojos = bonusService.adminGetAllBonusOrder();

        if (bonusOrderPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(jsonObject.getSize());
            jsonObject.setObject(bonusOrderPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


}
