package com.dexBusman.service;

import domain.bonusOrderPojo;
import domain.ordersPojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import qo.BonusorderWareBonusPojo;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-13-时间:17:31
 * @message:
 */
public interface BonusService {

    /*商家获取尚未参与直播带货秒杀的商品*/
    List<warePojo> getNoBonusWare(int busman_id);

    /*商品参与直播带湖哦信息*/
    int wareAttendBonus(warePojo ware);

    /*商人获取已经参与活动的商品*/
    List<warePojo> getAttendBonusWare(int busman_id);

    /*商人修改参加的商品信息*/
    int UpdateAttendBonusMessage(warePojo ware);

    /*商品退出直播带货活动*/
    int wareOutBonus(int ware_id,int busman_id);

    /*返回商人的全部秒杀订单*/
    List<BonusorderWareBonusPojo> getAllBonusOrder(int busman_id) throws ParseException;

    /*商人发出秒杀商品*/
    int sendWareByBusman(int bonusOrder_id,String expressNumber);


}
