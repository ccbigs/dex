package com.dexUser.dao;

import domain.ordersPojo;
import org.apache.ibatis.annotations.Param;
import qo.order_add_warePojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:11:38
 * @message:
 */
public interface OrdersDao {

    /*详情页面购买*/
    int setOrderForWareDetail(ordersPojo order);

    /*返回用户购买的订单*/
    List<order_add_warePojo> getUserOrder(int user_id);

    /*返回已签收的订单*/
    List<order_add_warePojo> getUserDidOrder(int user_id);

    /*返回用户全部的退换货订单*/
    List<order_add_warePojo> getUserReturnOrder(int order_id);

    /*取消订单*/
    int cancelOrder(int order_id);

    /*用户取消订单，返回商品原数量*/
    int cancelWare(@Param("ware_id") int ware_id,@Param("ware_number") int ware_number);

    /*用户取消订单，返回商品规格数量*/
    int cancelStandard(order_add_warePojo oawp);

    /*返回正在运输中的订单*/
    List<order_add_warePojo> getUserTransitOrder(int user_id);

    /*签收运输中的订单*/
    int setTransitToDid(int order_id);

    /*运输中的订单改换为退换货*/
    int transitToReturn(int order_id);

}
