package com.dexWare.serviceImpl;

import com.dexWare.service.HomeWareService;
import com.dexWare.dao.HomeWareDao;
import domain.categoriePojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:24
 * @message:
 */
@Service
public class HomeWareServiceImpl implements HomeWareService {

    @Autowired
    private HomeWareDao homeWareDao;

    @Override
    public List<warePojo> getAllWares() {
        return homeWareDao.getAllWares();
    }

    @Override
    public List<categoriePojo> getAllCategorie() {
        return homeWareDao.getAllCategorie();
    }


    @Override
    public List<warePojo> getWareByActive(String ware_active) {
        return homeWareDao.getWareByActive(ware_active);
    }

    @Override
    public List<warePojo> getWareByCategorie(int categorie_id) throws ParseException {
        List<warePojo> wareByCategorie = homeWareDao.getWareByCategorie(categorie_id);

        for (warePojo ware : wareByCategorie) {
            ware.setWare_date(getTimeNow.dataBasicToYMDHMS(ware.getWare_date()));
        }

        return wareByCategorie;
    }

    /*获取价格由高到低*/
    @Override
    public List<warePojo> priceFromHighToLow() throws ParseException {
        List<warePojo> warePojos = homeWareDao.priceFromHighToLow();

        for (warePojo warePojo : warePojos) {
            warePojo.setWare_date(getTimeNow.dataBasicToYMDHMS(warePojo.getWare_date()));
        }

        return warePojos;
    }

    @Override
    public List<warePojo> priceFromLowToHigh() throws ParseException {
        List<warePojo> warePojos = homeWareDao.priceFromLowToHigh();

        for (warePojo warePojo : warePojos) {
            warePojo.setWare_date(getTimeNow.dataBasicToYMDHMS(warePojo.getWare_date()));
        }

        return warePojos;
    }
}
