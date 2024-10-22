package com.dexStore.dao;

import domain.infoPojo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-20-时间:22:16
 * @message:
 */
public interface InformationDao {

    /*返回商家全部的公告信息*/
    List<infoPojo> GetInformationByBusman();

    /*返回网红文章数据*/
    List<infoPojo> GetInformationByUser();

    /*增加用户浏览量*/
    void increaseArticleViews(int info_id);

    /*返回商家文章详细数据*/
    infoPojo getInformationDetailByBusman(int info_id);

    /*返回网红文章详细数据*/
    infoPojo getInformationDetailByUser(int info_id);

    /*DEX商城用户点赞*/
    int readersLikeArticles(int info_id);

    /*DEX商城用户取消点赞*/
    int readersCancelFavoriteArticles(int info_id);
}
