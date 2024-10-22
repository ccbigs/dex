package com.dex.dao;

import domain.QuotientPricePojo;
import domain.carouselPojo;
import domain.phRecommendPojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import qo.AdminCarouselPojo;
import qo.AdminQutientOrderPojo;
import qo.AdminRecommendPojo;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:19
 * @message:
 */
public interface QutientDao {

    /*管理员获取全部的商位订单信息*/
    List<AdminQutientOrderPojo> adminGetAllQutientOrder();

    /*
     * @author: 左先生
     * @date: 2021-07-21 15:56
     * @description:轮播图
     */
    /*管理员获取轮播图的全部信息*/
    List<AdminCarouselPojo> adminGetAllCarousel();

    /*管理员修改轮播图的状态*/
    int adminModifyCarouselState(carouselPojo carousel);

    /*管理员删除轮播图*/
    @Delete("delete from hp_carousel where id=#{CarouselId}")
    int DeleteCarouse(int CarouselId);

    /*---------------------------------------------------------*/
    /*管理员获取推荐商品商位信息*/
    List<AdminRecommendPojo> adminGetAllRecommend();

    /*管理员更改推荐商品的状态*/
    int adminModifyRecommendState(phRecommendPojo recommend);

    /*管理员获取商位价格信息*/
    QuotientPricePojo adminGetAllQuitentPrice();

    /*管理员修改商位的价格信息*/
    int adminModifyQuitentPrice(QuotientPricePojo quotientPrice);
}
