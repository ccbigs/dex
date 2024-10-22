package com.dexBusman.dao;

import domain.activePojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:38
 * @message:
 */
@Mapper
public interface ActiveDao {
    /*商人查询未参与活动的商品*/
    List<warePojo> busmanNoActive(int busman_id);

    /*商人查询自己已参与活动的商品*/
    List<warePojo> getActiveDidWare(int busman_id);

   /* *//*商品参加活动成功，修改商品的活动属性*//*
    int ModifyWareActive(@Param("ware_active") String ware_active,@Param("ware_id") String ware_id);
*/
    /*商家添加满减活动*/
    int busmanAddFullReduction(activePojo active);

    /*商家添加打折活动*/
    int busmanAddDiscount(activePojo active);

    /*------------------推出业务的两个面向数据库的接口---------------------*/
    /*商家退出活动*//*
    int outActive(@Param("busman_id") int busman_id,@Param("ware_id") int ware_id);*/

    /*商家退出活动*/
    int outActiveWare(@Param("busman_id")int busman_id,@Param("ware_id") int ware_id);

    /*商家获取指定的参与活动的信息*/
    warePojo getWareActiveMessage(@Param("ware_id") int ware_id,@Param("busman_id") int busman_id);

    /*商家修改商品的打折信息*/
    int busmanUpdateDiscount(warePojo active);

    /*商家修改商品的满减信息*/
    int busmanUpdateFull(warePojo active);
}
