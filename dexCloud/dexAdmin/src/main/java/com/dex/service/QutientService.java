package com.dex.service;

import domain.QuotientPricePojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import qo.AdminCarouselPojo;
import qo.AdminQutientOrderPojo;
import qo.AdminRecommendPojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:21
 * @message:
 */
public interface QutientService {

    /*管理员获取全部的商位订单信息*/
    List<AdminQutientOrderPojo> adminGetAllQutientOrder() throws ParseException;

    /*管理员获取轮播图的全部信息*/
    List<AdminCarouselPojo> adminGetAllCarousel() throws ParseException;

    /*管理员修改轮播图的状态*/
    int adminModifyCarouselState(int id,String carousel);

    /*管理员删除轮播图*/
    int DeleteCarouse(int CarouselId);

    /*管理员获取推荐商品商位信息*/
    List<AdminRecommendPojo> adminGetAllRecommend();

    /*管理员更改推荐商品的状态*/
    int adminModifyRecommendState(int id,String recommend_state);

    /*管理员获取商位价格信息*/
    QuotientPricePojo adminGetAllQuitentPrice();

    /*管理员修改商位的价格信息*/
    int adminModifyQuitentPrice(QuotientPricePojo quotientPrice);
}
