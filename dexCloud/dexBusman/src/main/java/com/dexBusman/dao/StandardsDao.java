package com.dexBusman.dao;

import domain.standardsPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import qo.StandardWarePojo;
import utils.JsonObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:9:43
 * @message:
 */

public interface StandardsDao {

    /*通过管理员的id查询出他商品的全部规格信息*/
    List<StandardWarePojo> getStandardByBusman(int busman_id);

    /*添加新的商品规格信息*/
    int AddStandards(standardsPojo standards);

    /*添加规格信息时同步商品表中的总数量*/
    void updateWareNumber(@Param("number") int number,@Param("ware_id") int ware_id);

    /*管理员添加自己的商品规格的数量*/
    int busmanAddStandardNumber(@Param("standard_id") int standard_id,@Param("number") int number);

    /*商家删除选中的商品规格*/
    int deleteStandardByBusman(int standard_id);

    /*商家添加商品的同时，检测规格的价格是否小于ware中的价格*/
    int updateWarePrice(standardsPojo standard);

}
