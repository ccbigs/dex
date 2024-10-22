package com.dexBusman.controller;

import com.dexBusman.service.CategoriesService;
import domain.categoriePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:52
 * @message:
 */
@RequestMapping("/categorie")
@CrossOrigin
@RestController
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    /*返回全部的商品分类信息*/
    @PostMapping("/getAllCategorie")
    JsonObject getAllCategorie(){
        JsonObject jsonObject = new JsonObject();

        List<categoriePojo> Categories = categoriesService.getAllCategorie();

        if (Categories.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(Categories.size());
            jsonObject.setObject(Categories);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


}
