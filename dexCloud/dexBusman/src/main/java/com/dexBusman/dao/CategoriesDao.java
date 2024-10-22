package com.dexBusman.dao;

import domain.categoriePojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:53
 * @message:
 */
public interface CategoriesDao {

    /*返回全部的商品分类信息*/
    List<categoriePojo> getAllCategorie();

}
