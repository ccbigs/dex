package com.dexWare.dao;

import domain.activePojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:26
 * @message:
 */
@Repository
public interface DetailWareDao {

    /*用户您如商品详情页面，返回该商品的打折信息*/
    activePojo getWareActive(int ware_id);


    /*商品详情页面*/
    warePojo getWareById(int wareId);

    /*在详情页面购买商品后减少商品的数量*/
    int reduceWareForWareDetail(@Param("ware_id") int ware_id,@Param("num") int num);

    /*返回商家的地址信息*/
    String getBusmanAddress(int ware_id);

}
