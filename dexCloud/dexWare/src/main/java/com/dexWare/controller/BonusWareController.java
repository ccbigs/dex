package com.dexWare.controller;

import com.dexWare.service.BonusWareService;
import domain.bonusOrderPojo;
import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;
import utils.getTimeNow;

import java.security.spec.KeySpec;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:14:59
 * @message:
 */
@RestController
@RequestMapping("/bonus")
@CrossOrigin
public class BonusWareController {

    @Autowired
    private BonusWareService bonusWareService;

    /*1.返回秒杀页面的商品信息*/
    @PostMapping("/getWareByBonus")
    JsonObject getWareByBonus(int bonus_id){
        JsonObject jsonObject = new JsonObject();
        warePojo ware = bonusWareService.getWareByBonus(bonus_id);

        if (ware == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(ware);
        }

        return jsonObject;
    }

    /*2.返回参与秒杀信息*/
    @PostMapping("/getBonus")
    JsonObject getBonus(int bonus_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        bonusPojo bonus = bonusWareService.getBonus(bonus_id);

        if (bonus == null){
            jsonObject.setCode(500);
        }else {
            /*格式化时间*/
            bonus.setEnddate(getTimeNow.dataBasicToYMDHMS(bonus.getEnddate()));
            jsonObject.setCode(200);
            jsonObject.setObject(bonus);
        }

        return jsonObject;
    }

    /*3.返回商品规格信息*/
    @PostMapping("/getStandard")
    JsonObject getStandard(int bonus_id){
        JsonObject jsonObject = new JsonObject();
        List<standardsPojo> standard = bonusWareService.getStandard(bonus_id);

        if (standard != null  && standard.size() > 0){
            jsonObject.setSize(standard.size());
            jsonObject.setObject(standard);
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*3.游客购买了商品,生成相应订单*/
    @PostMapping("/TouristBuyBonusWare")
    JsonObject TouristBuyBonusWare(bonusOrderPojo bonusOrder){
        JsonObject jsonObject = new JsonObject();
        int result = bonusWareService.TouristBuyBonusWare(bonusOrder);
        if (result == 200){
            jsonObject.setCode(200);
        }else if (result == 202){
            jsonObject.setCode(202);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*4.时间到了，秒杀结束*/
    @PostMapping("/exitBonus")
    JsonObject theSecondKillIsOver(int ware_id,int bonus_id){
        JsonObject jsonObject = new JsonObject();

        if (bonusWareService.theSecondKillIsOver(ware_id, bonus_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        System.out.println("=================================================>退出活动");
        return jsonObject;
    }

}
