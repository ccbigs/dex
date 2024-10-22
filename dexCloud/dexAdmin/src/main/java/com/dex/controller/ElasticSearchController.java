package com.dex.controller;

import com.dex.service.ElasticSearchService;
import dto.ESAdminMsgDTO;
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
 * @date:日期:2021-09-09-时间:10:52
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchService elasticSearchService;


    /*返回管理界面3个节点的信息*/
    @PostMapping("/getNodeMessage")
    public JsonObject getNodeMessage(){
        JsonObject jsonObject = new JsonObject();
        List<ESAdminMsgDTO> nodeMessage = elasticSearchService.getNodeMessage();

        if (nodeMessage.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(nodeMessage.size());
            jsonObject.setObject(nodeMessage);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员同步ES和mysql中的商品信息*/
    @PostMapping("/adminSynchronizationWareData")
    public JsonObject adminSynchronizationWareData(String account) throws ParseException {
        JsonObject jsonObject = new JsonObject();

        if (elasticSearchService.adminSynchronizationWareData(account) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*管理员同步ES和mysql中的秒杀订单信息*/
    @PostMapping("/adminSynchronizationBonusOrderData")
    public JsonObject adminSynchronizationBonusOrderData(String account) throws ParseException {
        JsonObject jsonObject = new JsonObject();

        if (elasticSearchService.adminSynchronizationBonusOrderData(account) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*管理员同步ES和mysql中的订单信息*/
    @PostMapping("/adminSynchronizationOrderData")
    public JsonObject adminSynchronizationOrderData(String account) throws ParseException {
        JsonObject jsonObject = new JsonObject();

        if (elasticSearchService.adminSynchronizationOrderData(account) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


}
