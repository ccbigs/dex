package com.dex.controller;

import com.dex.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.AdminOrderPojo;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:58
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*管理员获取全部的订单信息*/
    @RequestMapping("/adminGetAllOrder")
    JsonObject adminGetAllOrder() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<AdminOrderPojo> AdminOrderPojos = orderService.adminGetAllOrder();

        if (AdminOrderPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(AdminOrderPojos.size());
            jsonObject.setObject(AdminOrderPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*管理员删除订单信息*/
    @RequestMapping("/adminDeleteOrder")
    JsonObject adminDeleteOrder(int id){
        JsonObject jsonObject = new JsonObject();

        if (orderService.adminDeleteOrder(id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
