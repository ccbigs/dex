package com.dexBusman.serviceImpl;

import com.dexBusman.dao.QuotientDao;
import com.dexBusman.service.QuotientService;
import domain.*;
import org.springframework.stereotype.Service;
import qo.RecommendWareOrderDTO;
import qo.carousel_ware_orderDTO;
import utils.JsonObject;
import utils.getTimeNow;
import vo.homeVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:58
 * @message:
 */
@Service
public class QuotientServiceImpl implements QuotientService {

    @Resource
    private QuotientDao quotientdao;

    @Override
    public homeVo getOnlineQuotient() {
        List<carouselPojo> onlineCarousel = quotientdao.getOnlineCarousel();
        List<warePojo> onlineRecommend = quotientdao.getOnlineRecommend();

        if (onlineCarousel.size() < 4){
            for (int i = onlineCarousel.size(); i < 4; i++) {
                carouselPojo carouselPojo = new carouselPojo();
                carouselPojo.setId(0);
                onlineCarousel.add(i,carouselPojo);
            }
        }

        if (onlineRecommend.size() < 10){
            for (int i = onlineRecommend.size(); i <10 ; i++) {
                warePojo ware=new warePojo();
                ware.setId(0);
                onlineRecommend.add(i,ware);
            }
        }

        homeVo homeVo = new homeVo();

        homeVo.setHp_recommend(onlineRecommend);
        homeVo.setCarousel(onlineCarousel);
        homeVo.setSize(2);
        homeVo.setCode(200);

        return homeVo;
    }

    @Override
    public JsonObject getQuotientPrice() {
        JsonObject jsonObject = new JsonObject();
        QuotientPricePojo quotientPrice = quotientdao.getQuotientPrice();
        if (quotientPrice == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(quotientPrice);
            jsonObject.setSize(1);
        }
        return jsonObject;
    }

    @Override
    public JsonObject createOrderByBusman(QuotientOrderPojo order) {
        JsonObject jsonObject = new JsonObject();
        int orderByBusman = quotientdao.createOrderByBusman(order);
        if(orderByBusman > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(order.getId());
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    @Override
    public JsonObject insertCarouselByBusman(carouselPojo carousel) {
        JsonObject jsonObject = new JsonObject();

        carousel.setCarousel_state("启用");
        carousel.setCreateDate(new getTimeNow().getTimeNowYMD());
        carousel.setEndDate(getTimeNow.getMonthAddYMS(carousel.getId()));

        if (quotientdao.insertCarouselByBusman(carousel) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    @Override
    public JsonObject insertRecommendByBusman(phRecommendPojo recommend) {
        JsonObject jsonObject = new JsonObject();

        recommend.setCreatedate(new getTimeNow().getTimeNowYMD());
        recommend.setEnddate(getTimeNow.getMonthAddYMS(recommend.getId()));
        recommend.setRecommend_state("启用");

        if (quotientdao.insertRecommendByBusman(recommend) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    @Override
    public JsonObject getDidBuyCarousel(int busman_id) {
        JsonObject jsonObject = new JsonObject();

        List<carousel_ware_orderDTO> didBuyCarousel = quotientdao.getDidBuyCarousel(busman_id);

        if (didBuyCarousel == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(didBuyCarousel.size());
            jsonObject.setObject(didBuyCarousel);
        }
        return jsonObject;
    }

    @Override
    public JsonObject getDidBuyRecommend(int busman_id) {
        JsonObject jsonObject = new JsonObject();
        List<RecommendWareOrderDTO> didBuyRecommend = quotientdao.getDidBuyRecommend(busman_id);

        if (didBuyRecommend == null){
            jsonObject.setCode(500);
        }else{
            jsonObject.setCode(200);
            jsonObject.setSize(didBuyRecommend.size());
            jsonObject.setObject(didBuyRecommend);
        }
        return jsonObject;
    }
}
