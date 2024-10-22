package com.dexStore.controller;

import com.dexStore.service.InformationService;
import domain.infoPojo;
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
 * @date:日期:2021-08-20-时间:22:16
 * @message:
 */
@RestController
@RequestMapping("/info")
@CrossOrigin
public class InformationController {

    @Autowired
    private InformationService informationService;

    /*增加用户浏览量*/
    @RequestMapping("/increaseArticleViews")
    void increaseArticleViews(int info_id){
        informationService.increaseArticleViews(info_id);
    }


    /*返回商人的公告信息*/
    @RequestMapping("/GetInformationByBusman")
    JsonObject GetInformationByBusman() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<infoPojo> infoPojos = informationService.GetInformationByBusman();

        if (infoPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(infoPojos.size());
            jsonObject.setObject(infoPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*返回网红文章*/
    @PostMapping("/GetInformationByUser")
    JsonObject GetInformationByUser() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<infoPojo> infoPojos = informationService.GetInformationByUser();

        if (infoPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(infoPojos.size());
            jsonObject.setObject(infoPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*返回商家文章详细数据*/
    @PostMapping("/getInformationDetailByBusman")
    JsonObject getInformationDetailByBusman(int info_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        infoPojo info = informationService.getInformationDetailByBusman(info_id);

        if ("商家".equals(info.getTitle_type()) || "全部".equals(info.getTitle_type())){
            jsonObject.setCode(200);
            jsonObject.setObject(info);
            jsonObject.setSize(1);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*返回网红文章详细数据*/
    @PostMapping("/getInformationDetailByUser")
    JsonObject getInformationDetailByUser(int info_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        infoPojo info = informationService.getInformationDetailByUser(info_id);

        if ("网红".equals(info.getTitle_type()) || "全部".equals(info.getTitle_type())){
            jsonObject.setCode(200);
            jsonObject.setObject(info);
            jsonObject.setSize(1);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }




    /*DEX商城用户点赞*/
    @PostMapping("/readersLikeArticles")
    JsonObject readersLikeArticles(int info_id){
        JsonObject jsonObject = new JsonObject();

        if (informationService.readersLikeArticles(info_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*DEX商城用户取消点赞*/
    @PostMapping("/readersCancelFavoriteArticles")
    JsonObject readersCancelFavoriteArticles(int info_id){
        JsonObject jsonObject = new JsonObject();

        if (informationService.readersCancelFavoriteArticles(info_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
