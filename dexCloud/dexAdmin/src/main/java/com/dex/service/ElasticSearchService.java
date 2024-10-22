package com.dex.service;

import dto.ESAdminMsgDTO;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:11:49
 * @message:
 */
public interface ElasticSearchService {

    /*返回管理界面3个节点的信息*/
    List<ESAdminMsgDTO> getNodeMessage();

    /*管理员同步ES和mysql中的商品信息*/
    int adminSynchronizationWareData(String account) throws ParseException;


    /*管理员同步ES和mysql中的秒杀订单信息*/
    int adminSynchronizationBonusOrderData(String account) throws ParseException;


    /*管理员同步ES和mysql中的订单信息*/
    int adminSynchronizationOrderData(String account) throws ParseException;

}
