package com.dexBusman.service;

import domain.activePojo;
import domain.warePojo;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:37
 * @message:
 */
public interface ActiveService {

    /*商人查询未参与活动的商品*/
    JsonObject busmanNoActive(int busman_id);

    /*商人查询自己已参与活动的商品*/
    JsonObject getActiveDidWare(int busman_id);

   /* *//*商品参加活动成功，修改商品的活动属性*//*
    JsonObject ModifyWareActive(String ware_active,String ware_id);*/

    /*商家添加满减活动*/
    JsonObject busmanAddFullReduction(activePojo active);

    /*商家添加打折活动*/
    JsonObject busmanAddDiscount(activePojo active);

    /*商家退出活动*/
    JsonObject outActive(int busman_id,int ware_id);

    /*商家获取指定的参与活动的信息*/
    JsonObject getWareActiveMessage(int ware_id,int busman_id);

    /*商家修改商品的打折信息*/
    JsonObject busmanUpdateDiscount(warePojo active);

    /*商家修改商品的满减信息*/
    JsonObject busmanUpdateFull(warePojo active);
}
