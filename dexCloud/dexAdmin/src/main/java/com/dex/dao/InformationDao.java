package com.dex.dao;

import domain.infoPojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-18-时间:16:02
 * @message:
 */
public interface InformationDao {

    /*管理员发布信息通知*/
    int adminPublicInformation(infoPojo info);

    /*管理员查看文章信息*/
    List<infoPojo> adminGetInformation();

    /*获取文章的全部数量*//*
    int getTotalInformationNumber();*/

    /*管理员查看指定文章的内容*/
    infoPojo getInformationDetail(int info_id);

    /*用户点赞文章*/
    int readersLikeArticles(int info_id);

    /*用户取消点赞文章*/
    int readersCancelFavoriteArticles(int info_id);

    /*用户打开文章，增加文章浏览量*/
    int increaseArticleViews(int info_id);

    /*管理员删除文章*/
    int deleteInformationById(int info_id);

    /*管理员搜索文章内容*/
    List<infoPojo> researchInformationByTitle(@Param("SearchTitle") String title);

    /*管理员更新文章*/
    int adminUpdateInformation(infoPojo info);
}
