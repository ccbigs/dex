package com.dexWare.service;

import domain.bonusOrderPojo;
import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:15:06
 * @message:
 */
public interface BonusWareService {

    /*1.返回秒杀页面的商品信息*/
    warePojo getWareByBonus(int bonus_id);

    /*2.返回参与秒杀信息*/
    bonusPojo getBonus(int bonus_id);

    /*3.返回商品规格信息*/
    List<standardsPojo> getStandard(int bonus_id);

    /*3.游客购买了商品,生成相应订单*/
    int TouristBuyBonusWare(bonusOrderPojo bonusOrder);

    /*4.时间到了，秒杀结束*/
    int theSecondKillIsOver(int ware_id,int bonus_id);
}
