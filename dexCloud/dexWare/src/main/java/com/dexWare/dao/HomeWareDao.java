package com.dexWare.dao;

import domain.categoriePojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:24
 * @message:
 */
@Repository
public interface HomeWareDao {

    /*返回全部的商品分类信息*/
    List<categoriePojo> getAllCategorie();


    /*获取全部商品*/
    List<warePojo> getAllWares();


    /*用户点击商品分类，展现分类信息*/
    List<warePojo> getWareByActive(String ware_active);

    /*用户页面：获取商品分类的所有商品*/
    List<warePojo> getWareByCategorie(int categorie_id);

    /*价格由高到低排序*/
    @Select("select * from ware where ware_state='售卖中' order by ware_price desc;")
    List<warePojo> priceFromHighToLow();


    /*价格有低到高*/
    @Select("select * from ware where ware_state='售卖中' order by ware_price asc;")
    List<warePojo> priceFromLowToHigh();

}
