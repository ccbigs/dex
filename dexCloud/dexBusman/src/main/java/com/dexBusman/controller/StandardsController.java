package com.dexBusman.controller;

import com.dexBusman.service.StandardsService;
import domain.standardsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.StandardWarePojo;
import utils.JsonObject;
import utils.getTimeNow;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:9:43
 * @message:
 */
@RequestMapping("/Standard")
@CrossOrigin
@RestController
public class StandardsController {

    @Autowired
    private StandardsService standardsService;


    /*管理员添加自己的商品规格的数量*/
    @PostMapping("/busmanAddStandardNumber")
    JsonObject busmanAddStandardNumber(int standard_id,int number,int ware_id){
        JsonObject jsonObject = new JsonObject();

        if (standardsService.busmanAddStandardNumber(standard_id, number, ware_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*通过管理员的id查询出他商品的全部规格信息*/
    @PostMapping("/getStandardByBusman")
    JsonObject getStandardByBusman(int busman_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<StandardWarePojo> standards = standardsService.getStandardByBusman(busman_id);

        if (standards.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(standards.size());
            jsonObject.setObject(standards);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*添加新的商品规格信息*/
    @PostMapping("/AddStandards")
    JsonObject AddStandards(standardsPojo standards){
        JsonObject jsonObject = new JsonObject();
        standards.setStandards_number(standards.getStandards_total());
        standards.setCreate_time(new getTimeNow().getTimeNowYMDHMS());

        if (standardsService.AddStandards(standards) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;

    }


    /*商家删除选中的商品规格*/
    @PostMapping("/deleteStandardByBusman")
    JsonObject deleteStandardByBusman(int standard_id){
        JsonObject jsonObject = new JsonObject();

        if (standardsService.deleteStandardByBusman(standard_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
