package com.dex.controller;

import com.dex.service.QutientService;
import domain.QuotientPricePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.AdminCarouselPojo;
import qo.AdminQutientOrderPojo;
import qo.AdminRecommendPojo;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:18
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class QutientController {

    @Autowired
    private QutientService qutientService;


    /*管理员获取全部的商位订单信息*/
    @RequestMapping("/adminGetAllQutientOrder")
    JsonObject adminGetAllQutientOrder() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<AdminQutientOrderPojo> adminQutientOrderPojos = qutientService.adminGetAllQutientOrder();

        if (adminQutientOrderPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(adminQutientOrderPojos.size());
            jsonObject.setObject(adminQutientOrderPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }



    /*管理员获取全部的轮播图图片信息*/
    @RequestMapping("/adminGetAllCarousel")
    JsonObject adminGetAllCarousel() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<AdminCarouselPojo> adminCarouselPojos = qutientService.adminGetAllCarousel();

        if (adminCarouselPojos.size() >0){
            jsonObject.setCode(200);
            jsonObject.setSize(adminCarouselPojos.size());
            jsonObject.setObject(adminCarouselPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员修改轮播图的状态*/
    @RequestMapping("/adminModifyCarouselState")
    JsonObject adminModifyCarouselState(int id,String carousel_state){
        JsonObject jsonObject = new JsonObject();

        if (qutientService.adminModifyCarouselState(id, carousel_state) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员删除轮播图*/
    @PostMapping("/DeleteCarouse")
    JsonObject DeleteCarouse(int CarouselId){
        JsonObject jsonObject = new JsonObject();

        if (qutientService.DeleteCarouse(CarouselId) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员获取全部的推荐商位信息*/
    @RequestMapping("/adminGetAllRecommend")
    JsonObject adminGetAllRecommend(){
        JsonObject jsonObject = new JsonObject();
        List<AdminRecommendPojo> adminRecommendPojos = qutientService.adminGetAllRecommend();

        if (adminRecommendPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(adminRecommendPojos.size());
            jsonObject.setObject(adminRecommendPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;

    }

    /*管理员修改推荐商位状态*/
    @RequestMapping("/adminModifyRecommendState")
    JsonObject adminModifyRecommendState(int id,String recommend_state){
        JsonObject jsonObject = new JsonObject();

        if (qutientService.adminModifyRecommendState(id, recommend_state) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员获取商位定价信息*/
    @RequestMapping("/adminGetAllQuitentPrice")
    JsonObject adminGetAllQuitentPrice(){
        JsonObject jsonObject = new JsonObject();
        QuotientPricePojo quotientPricePojo = qutientService.adminGetAllQuitentPrice();

        if (quotientPricePojo == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(1);
            jsonObject.setObject(quotientPricePojo);
        }

        return jsonObject;
    }

    /*管理员修改商位的价格信息*/
    @RequestMapping("/adminModifyQuitentPrice")
    JsonObject adminModifyQuitentPrice(QuotientPricePojo quotientPrice){
        JsonObject jsonObject = new JsonObject();

        if (qutientService.adminModifyQuitentPrice(quotientPrice) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }
}
