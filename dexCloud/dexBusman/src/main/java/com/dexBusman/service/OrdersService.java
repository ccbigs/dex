package com.dexBusman.service;

import domain.bonusOrderPojo;
import domain.ordersPojo;
import dto.ESOrdersDTO;
import org.apache.ibatis.annotations.Select;
import qo.BonusOrderWarePojo;
import qo.order_add_warePojo;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:52
 * @message:
 */
public interface OrdersService {

    /*商家的所有订单*/
    JsonObject busmanGetOrders(int busman_id);

    /*商家发出货物*/
    JsonObject busmanSendWare(int id,String expressNumber);

    /*商家签收货物*/
    JsonObject busmanSignWare(int id);

    /*商家搜索属于自己的订单*/
    JsonObject busmanSearchOrder(int busman_id,int order_id);

    /*商家搜索自己的订单，根据ES来搜索*/
    order_add_warePojo busmanSearchOrderByES(int busman_id, int order_id);

    /*商家搜索数据库中的秒杀订单信息*/
    BonusOrderWarePojo busmanSearchBonusOrder(int busman_id, int order_id);
}
