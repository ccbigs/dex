package com.dexBusman.dao;

import domain.*;
import qo.RecommendWareOrderDTO;
import qo.carousel_ware_orderDTO;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:56
 * @message:
 */
public interface QuotientDao {
    /*------------------最终由一个方法调用---------------------*/
    /*获取正常上线轮播图信息*/
    List<carouselPojo> getOnlineCarousel();

    /*获取已购商品*/
    List<warePojo> getOnlineRecommend();
    /*----------------------------------------------------*/

    /*返回商位的定价信息*/
    QuotientPricePojo getQuotientPrice();

    /*新建商家付款的商位订单*/
    Integer createOrderByBusman(QuotientOrderPojo order);

    /*订单成功生成之后，将商品写入到轮播图中*/
    int insertCarouselByBusman(carouselPojo carousel);

    /*订单成功生成之后，将商品写入到推荐商品中*/
    int insertRecommendByBusman(phRecommendPojo recommend);

    /*返回商人的已购买的轮播图商位*/
    List<carousel_ware_orderDTO> getDidBuyCarousel(int busman_id);

    /*返回商人的已购买的推荐商位*/
    List<RecommendWareOrderDTO> getDidBuyRecommend(int busman_id);
}
