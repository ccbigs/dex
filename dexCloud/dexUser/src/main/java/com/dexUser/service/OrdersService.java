package com.dexUser.service;

import domain.ordersPojo;
import qo.order_add_warePojo;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:11:38
 * @message:
 */
public interface OrdersService {

    /*详情页面购买*/
    JsonObject setOrderForWareDetail(ordersPojo order);

    /*返回用户购买的订单*/
    JsonObject getUserOrder(int user_id);

    /*返回已经购买的订单*/
    JsonObject getUserDidOrder(int user_id);

    /*返回正在运输中的订单*/
    JsonObject getUserTransitOrder(int user_id);

    /*退换货*/
    JsonObject getUserReturnOrder(int order_id);

    /*取消订单*/
    JsonObject cancelOrder(order_add_warePojo oawp);

    /*用户取消订单，返回商品原数量*/
    int cancelWare(int ware_id,int ware_number);

    /*签收运输中的订单*/
    JsonObject setTransitToDid(int order_id);

    /*运输中的订单改换为退换货*/
    JsonObject transitToReturn(int order_id);

}
