package com.dexStore.dao;

import domain.standardsPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:15:29
 * @message:
 */
public interface StandardDao {

    /*进入商品详情页面，获取该商品的全部商品规格*/
    List<standardsPojo> getStandardByWare(int ware_id);

    /*用户购买商品后，减少对应的商品模块的数量*/
    int reduceStandardNumberByUserBuyWare(@Param("standard_id") int standard_id,@Param("buy_number") int buy_number);
}