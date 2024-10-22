package com.dexBusman.serviceImpl;

import com.dexBusman.dao.BonusDao;
import com.dexBusman.service.BonusService;
import domain.bonusOrderPojo;
import domain.ordersPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qo.BonusorderWareBonusPojo;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-13-时间:17:31
 * @message:
 */
@Service
public class BonusServiceImpl implements BonusService {

    @Resource
    private BonusDao bonusDao;

    /*商家获取尚未参与直播带货秒杀的商品*/
    @Override
    public List<warePojo> getNoBonusWare(int busman_id) {
        return bonusDao.getNoBonusWare(busman_id);
    }

    /*商品参与直播带货信息*/
    @Override
    public int wareAttendBonus(warePojo ware) {
        return bonusDao.wareAttendBonus(ware);
    }

    /*商人获取已经参与活动的商品*/
    @Override
    public List<warePojo> getAttendBonusWare(int busman_id) {
        return bonusDao.getAttendBonusWare(busman_id);
    }

    /*商人修改已经参加的商品的信息*/
    @Override
    public int UpdateAttendBonusMessage(warePojo ware) {
        return bonusDao.UpdateAttendBonusMessage(ware);
    }

    /*商品退出直播带货活动*/
    @Override
    public int wareOutBonus(int ware_id, int busman_id) {
        return bonusDao.wareOutBonus(ware_id, busman_id);
    }

    /*返回全部的订单数*/
    @Override
    public List<BonusorderWareBonusPojo> getAllBonusOrder(int busman_id) throws ParseException {
        List<BonusorderWareBonusPojo> allBonusOrder = bonusDao.getAllBonusOrder(busman_id);

        for (BonusorderWareBonusPojo bonusPojo : allBonusOrder) {
            bonusPojo.setOrder_time(getTimeNow.dataBasicToYMDHMS(bonusPojo.getOrder_time()));
        }

        return allBonusOrder;
    }

    @Override
    public int sendWareByBusman(int bonusOrder_id,String expressNumber) {
        return bonusDao.sendWareByBusman(String.valueOf(bonusOrder_id),expressNumber);
    }


}
