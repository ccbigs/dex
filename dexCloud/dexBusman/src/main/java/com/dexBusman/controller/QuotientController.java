package com.dexBusman.controller;


import com.dexBusman.service.QuotientService;
import domain.QuotientOrderPojo;
import domain.carouselPojo;
import domain.phRecommendPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;
import utils.getTimeNow;
import vo.homeVo;

/*商位*/
@RequestMapping("/quotient")
@CrossOrigin
@RestController
public class QuotientController {
    @Autowired
    private QuotientService quotientService;

    @RequestMapping("/getOnlineQuotient")
    homeVo getOnlineQuotient(){
        return quotientService.getOnlineQuotient();
    }

    /*返回所有商位价格*/
    @RequestMapping("/getQuotientPrice")
    JsonObject getQuotientPrice(){
        return quotientService.getQuotientPrice();
    }

    /*新建商家付款的商位订单*/
    @RequestMapping("/createOrderByBusman")
    JsonObject createOrderByBusman(QuotientOrderPojo order){
        order.setOrder_time(new getTimeNow().getTimeNowYMD());
        return quotientService.createOrderByBusman(order);
    }

    /*订单成功生成之后，将商品写入到轮播图中*/
    @RequestMapping("/insertCarouselByBusman")
    JsonObject insertCarouselByBusman(carouselPojo carousel){
        System.out.println(carousel.toString());
        return quotientService.insertCarouselByBusman(carousel);
    }


    /*订单成功生成之后，将商品写入到推荐商品中*/
    @RequestMapping("/insertRecommendByBusman")
    JsonObject insertRecommendByBusman(phRecommendPojo recommend){
        return quotientService.insertRecommendByBusman(recommend);
    }

    /*返回商人的已购买的轮播图商位*/
    @RequestMapping("/getDidBuyCarousel")
    JsonObject getDidBuyCarousel(int busman_id){
        return quotientService.getDidBuyCarousel(busman_id);
    }

    /*返回商人的已购买的推荐商位*/
    @RequestMapping("/getDidBuyRecommend")
    JsonObject getDidBuyRecommend(int busman_id){
        return quotientService.getDidBuyRecommend(busman_id);
    }

}
