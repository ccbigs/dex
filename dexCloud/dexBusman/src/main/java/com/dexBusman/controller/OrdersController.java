package com.dexBusman.controller;

import com.dexBusman.service.OrdersService;
import domain.bonusOrderPojo;
import dto.ESOrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.BonusOrderWarePojo;
import qo.order_add_warePojo;
import utils.JsonObject;
import utils.getTimeNow;

import java.text.ParseException;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:49
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService orderService;

    /*商家所有的订单号*/
    @RequestMapping("/busmanGetOrders")
    JsonObject busmanGetOrders(int busman_id){
        return orderService.busmanGetOrders(busman_id);
    }

    /*商家发出货物*/
    @RequestMapping("/busmanSendWare")
    JsonObject busmanSendWare(int id,String expressNumber){
        return orderService.busmanSendWare(id,expressNumber);
    }

    /*商家签收货物*/
    @RequestMapping("/busmanSignWare")
    JsonObject busmanSignWare(int id){
        return orderService.busmanSignWare(id);
    }

    /*商家搜索自己的订单*/
    @RequestMapping("/busmanSearchOrder")
    JsonObject busmanSearchOrder(int busman_id,int order_id){
        return orderService.busmanSearchOrder(busman_id, order_id);
    }

    /*商家搜索自己的订单，根据ES来搜索*/
    @PostMapping("/busmanSearchOrderByES")
    JsonObject busmanSearchOrderByES(int busman_id,int order_id){
        JsonObject jsonObject = new JsonObject();
        order_add_warePojo esOrdersDTO = orderService.busmanSearchOrderByES(busman_id, order_id);
        if (esOrdersDTO == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(1);
            jsonObject.setObject(esOrdersDTO);
        }

        return jsonObject;
    }

    /*商家搜索数据库中的秒杀订单信息*/
    @PostMapping("/busmanSearchBonusOrder")
    JsonObject busmanSearchBonusOrder(int busman_id,int order_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        BonusOrderWarePojo bonusOrderPojo = orderService.busmanSearchBonusOrder(busman_id, order_id);

        if (bonusOrderPojo == null){
            jsonObject.setCode(500);
        }else {
            bonusOrderPojo.setOrder_time(getTimeNow.dataBasicToYMDHMS(bonusOrderPojo.getOrder_time()));
            jsonObject.setCode(200);
            jsonObject.setSize(1);
            jsonObject.setObject(bonusOrderPojo);
        }

        return jsonObject;
    }
}
