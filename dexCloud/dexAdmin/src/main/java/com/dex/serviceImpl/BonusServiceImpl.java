package com.dex.serviceImpl;

import com.dex.dao.BonusDao;
import com.dex.service.BonusService;
import domain.bonusOrderPojo;
import domain.bonusPojo;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-17-时间:16:57
 * @message:
 */
@Service
public class BonusServiceImpl implements BonusService {

    @Resource
    private BonusDao bonusDao;

    @Override
    public List<bonusPojo> adminGetAllBonus() throws ParseException {
        List<bonusPojo> bonusPojos = bonusDao.adminGetAllBonus();

        for (bonusPojo bonus : bonusPojos) {
            bonus.setBegindate(getTimeNow.dataBasicToYMDHMS(bonus.getBegindate()));
            bonus.setEnddate(getTimeNow.dataBasicToYMDHMS(bonus.getEnddate()));
            bonus.setResult_url("localhost:8080//stckkill/"+bonus.getId());
        }

        return bonusPojos;
    }

    @Override
    public List<bonusOrderPojo> adminGetAllBonusOrder() throws ParseException {
        List<bonusOrderPojo> bonusOrderPojos = bonusDao.adminGetAllBonusOrder();

        for (bonusOrderPojo bonusOrder : bonusOrderPojos) {
            bonusOrder.setOrder_time(getTimeNow.dataBasicToYMDHMS(bonusOrder.getOrder_time()));
        }

        return bonusOrderPojos;
    }
}
