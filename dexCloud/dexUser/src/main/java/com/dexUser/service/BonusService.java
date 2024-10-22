package com.dexUser.service;

import domain.bonusPojo;
import org.springframework.web.bind.annotation.PostMapping;
import qo.BonusBonusOrderPojo;
import utils.JsonObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-14-时间:11:03
 * @message:
 */
public interface BonusService {

    /*1.用户点击直播带货商品生成链接*/
    int userAttendBonusByWare(bonusPojo bonus);

    /*1.参与商品直播成功，编写redis缓存业务*/
    int successfulParticipationInSecondKill(int ware_id,int bonus_id);

    /*用户请求秒杀结果数据，返回直播数据和分红*/
    List<BonusBonusOrderPojo> getUserBonusDate(int user_id) throws ParseException;

    /*返回用户请求的秒杀全部收益和实际收益*/
    BigDecimal[] getUserProfit(int user_id);
}
