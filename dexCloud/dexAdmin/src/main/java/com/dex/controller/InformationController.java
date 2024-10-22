package com.dex.controller;

import com.dex.dao.InformationDao;
import com.dex.service.InformationService;
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
 * @date:日期:2021-08-18-时间:16:01
 * @message:发布信息
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class InformationController {

    @Autowired
    private InformationService informationService;

    /*管理员发布信息通知*/
    @PostMapping("/adminPublicInformation")
    JsonObject adminPublicInformation(infoPojo info){
        JsonObject jsonObject = new JsonObject();

        if (informationService.adminPublicInformation(info) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员查看文章信息*/
    @PostMapping("/adminGetInformation")
    JsonObject adminGetInformation() throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<infoPojo> infoPojos = informationService.adminGetInformation();

        if (infoPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(infoPojos.size());
            jsonObject.setObject(infoPojos);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

   /* *//*获取文章的全部数量*//*
    @PostMapping("/getTotalInformationNumber")
    JsonObject getTotalInformationNumber(){
        JsonObject jsonObject = new JsonObject();
        int totalInformationNumber = informationService.getTotalInformationNumber();

        if (totalInformationNumber > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(totalInformationNumber);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }*/

    /*管理员查看指定文章的内容*/
    @PostMapping("/getInformationDetail")
    JsonObject getInformationDetail(int info_id) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        infoPojo informationDetail = informationService.getInformationDetail(info_id);

        if (informationDetail == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(informationDetail);
            jsonObject.setSize(1);
        }

        return jsonObject;
    }



    /*用户点赞文章*/
    @PostMapping("/readersLikeArticles")
    JsonObject readersLikeArticles(int info_id){
        JsonObject jsonObject = new JsonObject();

        if (informationService.readersLikeArticles(info_id) > 0 ){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*用户取消点赞文章*/
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

    /*用户打开文章，增加文章浏览量*/
    @PostMapping("/increaseArticleViews")
    void increaseArticleViews(int info_id){
        informationService.increaseArticleViews(info_id);
    }

    /*管理员删除文章*/
    @PostMapping("/deleteInformationById")
    JsonObject deleteInformationById(int info_id){
        JsonObject jsonObject = new JsonObject();

        if (informationService.deleteInformationById(info_id) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


    /*管理员搜索文章内容*/
    @PostMapping("/researchInformationByTitle")
    JsonObject researchInformationByTitle(String title) throws ParseException {
        JsonObject jsonObject = new JsonObject();
        List<infoPojo> infoPojos = informationService.researchInformationByTitle(title);

        if (infoPojos == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(infoPojos.size());
            jsonObject.setObject(infoPojos);
        }

        return jsonObject;
    }

    /*管理员更新文章*/
    @PostMapping("/adminUpdateInformation")
    JsonObject adminUpdateInformation(infoPojo info){
        JsonObject jsonObject = new JsonObject();

        if (informationService.adminUpdateInformation(info) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


}
