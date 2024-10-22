package com.dexStore.dao;

import domain.carouselPojo;
import domain.tabPojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:23
 * @message:
 */
@Repository
public interface HomeDao {
    /*查询全部的轮播图*/
    List<carouselPojo> getAllCarousel();

    /*查询全部的上新商品*/
    List<warePojo> getAllNew();

    /*查询全部的热销单品*/
    List<warePojo> getAllHot();

    /*查询全部的推荐商品*/
    List<warePojo> getAllRecommend();


    /*根据用户的id返回用的的昵称*/
    @Select("select nickname from user where id = #{id};")
    String getUserNameById(int id);


    /*返回底部栏的所有标题信息*/
    List<tabPojo> getAllFootTab();
}
