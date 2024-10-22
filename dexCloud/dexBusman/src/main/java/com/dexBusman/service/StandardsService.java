package com.dexBusman.service;

import domain.standardsPojo;
import org.apache.ibatis.annotations.Param;
import qo.StandardWarePojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-23-时间:9:43
 * @message:
 */
public interface StandardsService {

    /*通过管理员的id查询出他商品的全部规格信息*/
    List<StandardWarePojo> getStandardByBusman(int busman_id) throws ParseException;

    /*添加新的商品规格信息*/
    int AddStandards(standardsPojo standards);

    /*管理员添加自己的商品规格的数量*/
    int busmanAddStandardNumber(int standard_id,int number,int ware_id);

    /*商家删除选中的商品规格*/
    int deleteStandardByBusman(int standard_id);

}
