package com.dexWare.controller;

import com.dexWare.service.HomeWareService;
import domain.categoriePojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:24
 * @message:
 */
@RestController
@RequestMapping("/wares")
@CrossOrigin
public class HomeWareController {

    @Autowired
    private HomeWareService homeWareService;

    /*返回全部的商品数据*/
    @RequestMapping("/getAllWare")
    public JsonObject getAllWare(){
        List<warePojo> allWares = homeWareService.getAllWares();
        JsonObject allWaresJosn = new JsonObject();
        allWaresJosn.setObject(allWares);
        allWaresJosn.setSize(allWares.size());
        allWaresJosn.setCode(200);
        return allWaresJosn;
    }

    /*返回全部的商品分类信息*/
    @PostMapping("/getAllCategorie")
    JsonObject getAllCategorie(){
        JsonObject jsonObject = new JsonObject();

        List<categoriePojo> Categories = homeWareService.getAllCategorie();

        if (Categories.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(Categories.size());
            jsonObject.setObject(Categories);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*用户点击商品分类，展现分类信息*/
    @RequestMapping("/getWareByActive")
    JsonObject getWareByActive(String ware_active){
        JsonObject jsonObject = new JsonObject();

        List<warePojo> wareByActive = homeWareService.getWareByActive(ware_active);

        if (wareByActive.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(wareByActive.size());
            jsonObject.setObject(wareByActive);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*
     * @author: 左先生
     * @date: 2021-07-23 10:39
     * @description:用户页面：获取商品分类的所有商品
     */
    @PostMapping("/getWareByCategorie")
    JsonObject getWareByCategorie(int categorie_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<warePojo> wareByCategorie = homeWareService.getWareByCategorie(categorie_id);

        if (wareByCategorie.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(wareByCategorie.size());
            jsonObject.setObject(wareByCategorie);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*
     * @author: 左先生
     * @date: 2021-09-10 21:48
     * @description:价格由高到低
     */
    @RequestMapping("/priceFromHighToLow")
    JsonObject priceFromHighToLow() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<warePojo> warePojos = homeWareService.priceFromHighToLow();

        if (warePojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(warePojos.size());
            jsonObject.setObject(warePojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*
     * @author: 左先生
     * @date: 2021-09-10 22:13
     * @description:价格由低到高
     */
    @RequestMapping("/priceFromLowToHigh")
    JsonObject priceFromLowToHigh() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<warePojo> warePojos = homeWareService.priceFromLowToHigh();

        if (warePojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(warePojos.size());
            jsonObject.setObject(warePojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


}
