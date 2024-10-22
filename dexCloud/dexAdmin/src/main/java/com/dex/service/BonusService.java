package com.dex.service;

import domain.bonusOrderPojo;
import domain.bonusPojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-17-时间:16:56
 * @message:
 */
public interface BonusService {

    /*管理员查看全部的秒杀信息*/
    List<bonusPojo> adminGetAllBonus() throws ParseException;

    /*管理员查看全部的秒杀订单*/
    List<bonusOrderPojo> adminGetAllBonusOrder() throws ParseException;
}
