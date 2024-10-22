package com.dexWare.service;

import domain.categoriePojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Select;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:24
 * @message:
 */
public interface HomeWareService {

    List<warePojo> getAllWares();

    /*用户点击商品分类，展现分类信息*/
    List<warePojo> getWareByActive(String ware_active);

    /*返回全部的商品分类信息*/
    List<categoriePojo> getAllCategorie();

    /*用户页面：获取商品分类的所有商品*/
    List<warePojo> getWareByCategorie(int categorie_id) throws ParseException;


    /*价格由高到低排序*/
    List<warePojo> priceFromHighToLow() throws ParseException;


    /*价格由低到高排序*/
    List<warePojo> priceFromLowToHigh() throws ParseException;


}
