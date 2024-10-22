package com.dexBusman.service;

import domain.QuotientOrderPojo;
import domain.carouselPojo;
import domain.phRecommendPojo;
import utils.JsonObject;
import vo.homeVo;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:57
 * @message:
 */
public interface QuotientService {
    /*获取正常上线轮播图信息，获取已购商品*/
    homeVo getOnlineQuotient();

    /*返回商位的定价信息*/
    JsonObject getQuotientPrice();

    /*新建商家付款的商位订单*/
    JsonObject createOrderByBusman(QuotientOrderPojo order);

    /*订单成功生成之后，将商品写入到轮播图中*/
    JsonObject insertCarouselByBusman(carouselPojo carousel);

    /*订单成功生成之后，将商品写入到推荐商品中*/
    JsonObject insertRecommendByBusman(phRecommendPojo recommend);

    /*返回商人的已购买的轮播图商位*/
    JsonObject getDidBuyCarousel(int busman_id);

    /*返回商人的已购买的推荐商位*/
    JsonObject getDidBuyRecommend(int busman_id);
}
