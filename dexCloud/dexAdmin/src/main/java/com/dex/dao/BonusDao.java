package com.dex.dao;

import domain.bonusOrderPojo;
import domain.bonusPojo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-17-时间:16:56
 * @message:
 */
@Repository
public interface BonusDao {

    /*管理员查看全部的秒杀信息*/
    List<bonusPojo> adminGetAllBonus();

    /*管理员查看全部的秒杀订单*/
    List<bonusOrderPojo> adminGetAllBonusOrder();
}
