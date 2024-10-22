package com.dexStore.serviceImpl;

import com.dexStore.dao.InformationDao;
import com.dexStore.service.InformationService;
import domain.infoPojo;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-20-时间:22:17
 * @message:
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Resource
    private InformationDao informationDao;

    @Override
    public List<infoPojo> GetInformationByBusman() throws ParseException {
        List<infoPojo> infoPojos = informationDao.GetInformationByBusman();

        for (infoPojo info : infoPojos) {
            info.setPublic_time(getTimeNow.dataBasicToYMDHMS(info.getPublic_time()));
        }

        return infoPojos;
    }

    /*返回网红的全部文章数据*/
    @Override
    public List<infoPojo> GetInformationByUser() throws ParseException {
        List<infoPojo> infoPojos = informationDao.GetInformationByUser();

        for (infoPojo info : infoPojos) {
            info.setPublic_time(getTimeNow.dataBasicToYMDHMS(info.getPublic_time()));
        }

        return infoPojos;
    }

    @Override
    public void increaseArticleViews(int info_id) {
        informationDao.increaseArticleViews(info_id);
    }

    /*返回文章的详细信息*/
    @Override
    public infoPojo getInformationDetailByBusman(int info_id) throws ParseException {
        infoPojo info = informationDao.getInformationDetailByBusman(info_id);

        info.setPublic_time(getTimeNow.dataBasicToYMDHMS(info.getPublic_time()));

        return info;
    }

    /*返回网红文章详细数据*/
    @Override
    public infoPojo getInformationDetailByUser(int info_id) throws ParseException {
        infoPojo IDBU = informationDao.getInformationDetailByUser(info_id);

        IDBU.setPublic_time(getTimeNow.dataBasicToYMDHMS(IDBU.getPublic_time()));

        return IDBU;
    }

    @Override
    public int readersLikeArticles(int info_id) {
        return informationDao.readersLikeArticles(info_id);
    }

    @Override
    public int readersCancelFavoriteArticles(int info_id) {
        return informationDao.readersCancelFavoriteArticles(info_id);
    }


}
