package com.dex.serviceImpl;

import com.dex.dao.InformationDao;
import com.dex.service.InformationService;
import domain.infoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-18-时间:16:02
 * @message:
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Resource
    private InformationDao informationDao;

    /*管理员发布文章*/
    @Override
    public int adminPublicInformation(infoPojo info) {
        info.setPublic_time(new getTimeNow().getTimeNowYMDHMS());
        return informationDao.adminPublicInformation(info);
    }

    /*文章分页传输数量*/
    @Override
    public List<infoPojo> adminGetInformation() throws ParseException {
        List<infoPojo> infoPojos = informationDao.adminGetInformation();

        for (infoPojo info : infoPojos) {
            info.setPublic_time(getTimeNow.dataBasicToYMDHMS(info.getPublic_time()));
        }

        return infoPojos;
    }

   /* *//*获取文章的全部数量*//*
    @Override
    public int getTotalInformationNumber() {
        return informationDao.getTotalInformationNumber();
    }*/



    /*管理员查看指定文章的内容*/
    @Override
    public infoPojo getInformationDetail(int info_id) throws ParseException {
        infoPojo informationDetail = informationDao.getInformationDetail(info_id);

        informationDetail.setPublic_time(getTimeNow.dataBasicToYMDHMS(informationDetail.getPublic_time()));

        return informationDetail;
    }

    @Override
    public int readersLikeArticles(int info_id) {
        return informationDao.readersLikeArticles(info_id);
    }

    @Override
    public int readersCancelFavoriteArticles(int info_id) {
        return informationDao.readersCancelFavoriteArticles(info_id);
    }

    @Override
    public int increaseArticleViews(int info_id) {
        return informationDao.increaseArticleViews(info_id);
    }

    @Override
    public int deleteInformationById(int info_id) {
        return informationDao.deleteInformationById(info_id);
    }

    @Override
    public List<infoPojo> researchInformationByTitle(String title) throws ParseException {
        List<infoPojo> infos = informationDao.researchInformationByTitle(title);

        for (infoPojo info : infos) {
            info.setPublic_time(getTimeNow.dataBasicToYMDHMS(info.getPublic_time()));
        }

        return infos;
    }

    @Override
    public int adminUpdateInformation(infoPojo info) {
        System.out.println(info);
        return informationDao.adminUpdateInformation(info);
    }
}
