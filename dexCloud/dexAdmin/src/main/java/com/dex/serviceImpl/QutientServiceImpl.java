package com.dex.serviceImpl;

import com.dex.dao.QutientDao;
import com.dex.service.QutientService;
import domain.QuotientPricePojo;
import domain.carouselPojo;
import domain.phRecommendPojo;
import org.springframework.stereotype.Service;
import qo.AdminCarouselPojo;
import qo.AdminQutientOrderPojo;
import qo.AdminRecommendPojo;
import utils.JudgeTime;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:22
 * @message:
 */
@Service
public class QutientServiceImpl implements QutientService {

    @Resource
    private QutientDao qutientDao;

    @Override
    public List<AdminQutientOrderPojo> adminGetAllQutientOrder() throws ParseException {
        return qutientDao.adminGetAllQutientOrder();
    }

    @Override
    public List<AdminCarouselPojo> adminGetAllCarousel() throws ParseException {
        List<AdminCarouselPojo> adminCarouselPojos = qutientDao.adminGetAllCarousel();

        for (AdminCarouselPojo acp : adminCarouselPojos) {
            if (JudgeTime.JudgeOneDayToOtherDayByYMD(acp.getEndDate(),new getTimeNow().getTimeNowYMD()) >= 0){
                acp.setType_time("尚未过期");
            }else {
                acp.setType_time("已过期");
            }
        }

        return adminCarouselPojos;
    }

    @Override
    public int adminModifyCarouselState(int id, String carousel_state) {
        carouselPojo carouselPojo = new carouselPojo();

        carouselPojo.setId(id);
        if ("禁用轮播图".equals(carousel_state)){
            carouselPojo.setCarousel_state("停用");
        }else if ("启用轮播图".equals(carousel_state)){
            carouselPojo.setCarousel_state("启用");
        }

        return qutientDao.adminModifyCarouselState(carouselPojo);
    }

    @Override
    public int DeleteCarouse(int CarouselId) {
        return qutientDao.DeleteCarouse(CarouselId);
    }

    @Override
    public List<AdminRecommendPojo> adminGetAllRecommend() {
        return qutientDao.adminGetAllRecommend();
    }

    @Override
    public int adminModifyRecommendState(int id, String recommend_state) {
        phRecommendPojo phRecommend = new phRecommendPojo();

        phRecommend.setId(id);
        if ("禁用推荐商品".equals(recommend_state)){
            phRecommend.setRecommend_state("停用");
        }else if ("启用推荐商品".equals(recommend_state)){
            phRecommend.setRecommend_state("启用");
        }
        return qutientDao.adminModifyRecommendState(phRecommend);
    }

    @Override
    public QuotientPricePojo adminGetAllQuitentPrice() {
        return qutientDao.adminGetAllQuitentPrice();
    }

    @Override
    public int adminModifyQuitentPrice(QuotientPricePojo quotientPrice) {
        return qutientDao.adminModifyQuitentPrice(quotientPrice);
    }
}
