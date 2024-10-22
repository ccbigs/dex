package com.dexBusman.dao;


import domain.ordersPojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import qo.order_add_warePojo;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VisualDao {

    /*返回左上角的销售额分析柱状图*/
    List<ordersPojo> salesVolume(int busman_id);

    /*返回商品的数据*/
    List<warePojo> getWareByBusman(int busman_id);

    /*返回商品和订单和商品的收货地址信息*/
    List<order_add_warePojo> getOrderAddressWare(int busman_id);


    /*返回商家全部的普通订单金额*/
    @Select("select sum(orders_totalprice) from orders where busman_id=#{busman_id}")
    BigDecimal getOrderSaleByBusman(int busman_id);

    /*返回商家的秒杀订单的全部金额*/
    @Select("select sum(bonus_totalprice) from bonusorder where busman_id=#{busman_id};")
    BigDecimal getBonusOrderSaleByBusman(int busman_id);

    /*获得秒杀订单该商家的订单数*/
    @Select("select count(*) from bonusorder where busman_id=#{busman_id};")
    int getBonusOrderTotalByBusman(int busman_id);

    /*获得普通订单的数量*/
    @Select("select count(*) from orders where busman_id=#{busman_id};")
    int getOrdersTotalByBusman(int busman_id);

    /*获得销售数量*/
    @Select("select sum(ware_totalnumber-ware.ware_number) from ware where busman_id=#{busman_id}")
    int getAllSaleNumber(int busman_id);

    /*返回当前在售商品的七日出货图*/
    List<ordersPojo> getOrderByFive(int busman_id);

}
