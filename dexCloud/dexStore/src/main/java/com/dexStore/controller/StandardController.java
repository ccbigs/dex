package com.dexStore.controller;

import com.dexStore.service.StandardService;
import domain.standardsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:15:26
 * @message:
 */
@RestController
@RequestMapping("/standards")
@CrossOrigin
public class StandardController {

    @Autowired
    private StandardService standardService;

    /*进入商品详情页面，获取该商品的全部商品规格*/
    @PostMapping("/getStandardByWare")
    JsonObject getStandardByWare(int ware_id){
        JsonObject jsonObject = new JsonObject();
        List<standardsPojo> standards = standardService.getStandardByWare(ware_id);

        if (standards.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setObject(standards);
            jsonObject.setSize(standards.size());
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*用户购买商品后，减少对应的商品模块的数量,为商品详情页面和购物车提供服务*/
    @PostMapping("reduceStandardNumberByUserBuyWare")
    JsonObject reduceStandardNumberByUserBuyWare(int standard_id,int buy_number){
        JsonObject jsonObject = new JsonObject();

        if (standardService.reduceStandardNumberByUserBuyWare(standard_id, buy_number) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
