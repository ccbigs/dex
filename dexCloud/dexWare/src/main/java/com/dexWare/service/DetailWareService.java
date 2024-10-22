package com.dexWare.service;

import domain.activePojo;
import domain.warePojo;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:26
 * @message:
 */
public interface DetailWareService {

    /*用户您如商品详情页面，返回该商品的打折信息*/
    activePojo getWareActive(int ware_id);

    warePojo getWareById(int wareId);

    /*在详情页面购买商品后减少商品的数量*/
    JsonObject reduceWareForWareDetail(int ware_id, int num);

    /*返回商家的地址信息*/
    String getBusmanAddress(int ware_id);
}
