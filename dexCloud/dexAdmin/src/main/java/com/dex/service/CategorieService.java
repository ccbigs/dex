package com.dex.service;

import domain.categoriePojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:32
 * @message:
 */
public interface CategorieService {
    /*管理员获取全部的商品分类信息*/
    List<categoriePojo> adminGetAllCategorie() throws ParseException;

    /*管理员新增商品分类信息*/
    int adminAddCategorie(categoriePojo categorie);

    /*管理员修改商品分类信息*/
    int adminEditCategorie(categoriePojo categorie);

    /*管理员删除商品分类信息*/
    int adminDeleteCategorie(int categorie_id);
}
