package com.dexWare.manager;

import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:15:05
 * @message:
 */
public interface BonusWareManager {


    /*1.返回秒杀页面的商品信息*/
    warePojo getWareByBonus(int ware_id);


    /*2.返回秒杀信息*/
    bonusPojo getBonus(int bonus_id);

    /*3.返回全部的商品规格信息*/
    List<standardsPojo> getAllStandard(int bonus_id);

    /*4.获取指定的商品规格信息*/
    standardsPojo getStandardByID(int bonus_id,int standard_id);

    /*5.商品退出活动，清除redis中的商品信息*/
    warePojo clearAndReturnWareMessage(int ware_id);

    /*6.购买商品，商品大于0，减少商品数量，商品小于0，关闭秒杀*/
    int commoditySpikeBusinessLogic(int ware_id,int buy_number,int standard_id,int bonus_id);

    /*7.清除bonus信息*/
    Boolean clearBonus(int bonus_id);

    /*8.返回并清除redis中的stanards信息*/
    Boolean clearStandardMessage(int bonus_id);

}
