package com.dexBusman.service;

import domain.warePojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import utils.JsonObject;

import java.text.ParseException;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:38
 * @message:
 */
public interface WaresService {

    /*商家上传商品*/
    JsonObject busmanAddWare(warePojo ware);

    /*根据商家的id返回商家的所有商品*/
    JsonObject getWareByBusman(int busman_id) throws ParseException;

    /*商家修改自己的商品信息*/
    JsonObject busmanUpdateWareMessage(warePojo ware);

    /*商家添加库存*/
    JsonObject busmanUpdateWareNumber(int id,int number);

    /*商家下架商品*/
    JsonObject busmanUnderCarriageWare(int id);

    /*商家上架商品*/
    JsonObject busmanGroundWare(int id);

    /*获取指定商家售卖中的商品*/
    JsonObject busmanAlreadyGroundWare(int busman_id);

    /*商家删除指定的物品*/
    int busmanDeleteWare(int busmanId,int wareId);
}
