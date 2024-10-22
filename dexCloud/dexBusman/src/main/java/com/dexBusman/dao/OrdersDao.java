package com.dexBusman.dao;

import domain.addressPojo;
import domain.bonusOrderPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;
import qo.BonusOrderWarePojo;
import qo.order_add_warePojo;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:51
 * @message:
 */
@Mapper
public interface OrdersDao {
    /*商家的所有订单*/
    List<order_add_warePojo> busmanGetOrders(int busman_id);

    /*商家发出货物*/
    int busmanSendWare(@Param("id") String id,@Param("expressNumber") String expressNumber);

    /*商家签收货物*/
    int busmanSignWare(int id);

    /*商家搜索属于自己的订单*/
    order_add_warePojo busmanSearchOrder(int order_id);

    /*获取订单的收货地址*/
    @Select("select * from address where id=#{address_id}")
    addressPojo getAddressByAddress_id(int address_id);

    /*商家搜索数据库中的秒杀订单信息*/
    BonusOrderWarePojo busmanSearchBonusOrder(@Param("busman_id") int busman_id, @Param("order_id") int order_id);
}
