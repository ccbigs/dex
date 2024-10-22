package com.dex.controller;

import com.dex.service.CategorieService;
import domain.categoriePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;
import utils.getTimeNow;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:30
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    /*管理员获取全部的商品分类信息*/
    @PostMapping("/adminGetAllCategorie")
    JsonObject adminGetAllCategorie() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<categoriePojo> categoriePojos = categorieService.adminGetAllCategorie();

        if (categoriePojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(categoriePojos.size());
            jsonObject.setObject(categoriePojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员新增商品分类信息*/
    @PostMapping("/adminAddCategorie")
    JsonObject adminAddCategorie(categoriePojo categorie){
        categorie.setCategorie_create_time(new getTimeNow().getTimeNowYMDHMS());

        JsonObject jsonObject = new JsonObject();

        if (categorieService.adminAddCategorie(categorie) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员修改商品信息*/
    @PostMapping("/adminEditCategorie")
    JsonObject adminEditCategorie(categoriePojo categorie){
        JsonObject jsonObject = new JsonObject();

        categorie.setCategorie_create_time(new getTimeNow().getTimeNowYMDHMS());

        if (categorieService.adminEditCategorie(categorie) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员删除商品分类信息*/
    @RequestMapping("/adminDeleteCategorie")
    JsonObject adminDeleteCategorie(int categorie_id){
        JsonObject jsonObject = new JsonObject();

        if (categorieService.adminDeleteCategorie(categorie_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
