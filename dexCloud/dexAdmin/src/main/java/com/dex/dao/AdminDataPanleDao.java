package com.dex.dao;

import domain.bonusOrderPojo;
import domain.busmanPojo;
import domain.ordersPojo;
import domain.userPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-06-时间:14:05
 * @message:管理员面板数据
 */
@Mapper
public interface AdminDataPanleDao {

    /*redisTemplate.opsForValue().get("AllUser");
        redisTemplate.opsForValue().get("AllBusman");
        redisTemplate.opsForValue().get("AllOrder");
        redisTemplate.opsForValue().get("AllComment");
        redisTemplate.opsForValue().get("AllSumTurnover");
        redisTemplate.opsForValue().get("AllActuallyTurnover");
        redisTemplate.opsForValue().get("LimitQutient");
        redisTemplate.opsForValue().get("AllQutientTurnover");*/

    /*获取全部的用户认数*/
    @Select("select count(*) from user;")
    int getAllUser();

    /*获取所有的商家人数*/
    @Select("select count(*) from busman")
    int getAllBusman();

    /*获取所有的订单信息*/
    @Select("select count(*) from orders")
    int getAllOrder();

    /*获取所有的秒杀订单数量*/
    @Select("select count(*) from bonusorder")
    int getAllBonusOrder();

    /*获取全部的评论数*/
    @Select("select count(*) from comment")
    int getAllComment();

    /*获取累计交易金额*/
    /*普通订单*/
    @Select("select sum(orders_totalprice) from orders;")
    BigDecimal getAllSumTurnover();
    /*秒杀订单*/
    @Select("select sum(bonus_totalprice) from bonusorder;")
    BigDecimal getAllbonusTotalPrice();


    /*获取实际的成交金额*/
    /*普通订单辑视成交金额*/
    @Select("select sum(orders_totalprice) from orders where logistics_status='已关闭'")
    BigDecimal getAllActuallyTurnover();
    /*秒杀订单的实际成交额*/
    @Select("select sum(bonus_totalprice) from bonusorder where order_state='运输中';")
    BigDecimal getAllActuallyBonusTurnover();


    /*获取剩余的商位*/
    /*轮播图剩余商位*/
    @Select("select 4-count(*) from hp_carousel where carousel_state='启用'")
    int getLimitCarousel();
    /*推荐剩余的商位*/
    @Select("select 10-count(*) from hp_recommend where recommend_state='启用'")
    int getLimitRecommend();

    /*轮播图成交金额*/
    @Select("select sum(pay_price) from quotient_orders;")
    BigDecimal AllQutientTurnover();


    /*第一块面板查看七天之内所有秒杀的数据*/
    List<bonusOrderPojo> getSevenDayBonusOrder(@Param("tomorrow") String tomorrow,@Param("lastWeek") String lastWeek);
    /*第一板块查看七天之内所有普通购买产生的数据*/
    List<ordersPojo> getSevenDayOrder(@Param("tomorrow") String tomorrow,@Param("lastWeek") String lastWeek);


}
