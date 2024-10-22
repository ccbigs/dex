package com.dexWare.serviceImpl;

import com.dexWare.dao.BonusWareDao;
import com.dexWare.manager.AntherRedisMsg;
import com.dexWare.manager.BonusWareManager;
import com.dexWare.manager.KafkaProducerMag;
import com.dexWare.service.BonusWareService;
import com.dexWare.utils.ActivityPriceCalculation;
import domain.bonusOrderPojo;
import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JsonObject;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:15:06
 * @message:
 */
@Service
public class BonusWareServiceImpl implements BonusWareService {

    @Autowired
    private BonusWareDao bonusWareDao;

    @Resource
    private BonusWareManager bonusWareManager;

    @Autowired
    private AntherRedisMsg antherRedisMsg;

    @Resource
    private KafkaProducerMag kafkaProducerMag;

    /*1.返回秒杀页面的商品信息*/
    @Override
    public warePojo getWareByBonus(int bonus_id) {
        /*添加游客活跃数量：一打开页面，就加一*/
        antherRedisMsg.savePeopleActive();
        /*如果返回的bonus为null，那么也就没必要返回商品了*/
        bonusPojo bonus=bonusWareManager.getBonus(bonus_id);
        if (bonus == null){
            return null;
        }else {
            return bonusWareManager.getWareByBonus(bonusWareManager.getBonus(bonus_id).getWare_id());
        }
    }

    /*2.返回秒杀信息*/
    @Override
    public bonusPojo getBonus(int bonus_id) {
        return bonusWareManager.getBonus(bonus_id);
    }

    @Override
    public List<standardsPojo> getStandard(int bonus_id) {
        return bonusWareManager.getAllStandard(bonus_id);
    }

    /*3.用户购买了，下订单订单，商品数量减少，商品规格一样减少*/
    @Override
    public int TouristBuyBonusWare(bonusOrderPojo bonusOrder) {
        System.out.println(bonusOrder);
        int flag=200;
        /*数据库商品数量是否减少成功，即商品数量大于0*/
        int result = bonusWareManager.commoditySpikeBusinessLogic(bonusOrder.getWare_id(), bonusOrder.getBuy_number(),bonusOrder.getStandard_id(),bonusOrder.getBonus_id());
        if (result == 500 || result == 202){
            /*购买失败*/
            flag = result;
        }else {
            /*购买成功，商品数量不为0*/
            warePojo ware = bonusWareManager.getWareByBonus(bonusOrder.getWare_id());
            bonusPojo bonus = bonusWareManager.getBonus(bonusOrder.getBonus_id());
            standardsPojo standard = bonusWareManager.getStandardByID(bonusOrder.getBonus_id(), bonusOrder.getStandard_id());
            /*将商品规模价格赋值给商品价格*/
            ware.setWare_price(standard.getStandards_price().doubleValue());

            /*完善订单对象*/
            bonusOrder.setUser_id(bonus.getUser_id());
            bonusOrder.setBusman_id(ware.getBusman_id());
            /*计算满减或者打折后的价格*/
            bonusOrder.setBonus_totalprice(ActivityPriceCalculation.PriceCalculation(ware,bonusOrder.getBuy_number()));
            /*添加时间*/
            bonusOrder.setOrder_time(getTimeNow.getStaticTimeNowYMDHMS());
            /*添加用户的总分红*/
            bonusOrder.setWare_bonusmoney(bonus.getBonus_money().multiply(new BigDecimal(bonusOrder.getBuy_number())));

            /*加入数据库的订单,这个订单数据要发送给kafka消息组件，来进行削峰*/
            System.out.println("进入？");
            kafkaProducerMag.sendProducerMessage(bonusOrder);
            /*bonusWareDao.TouristBuyBonusWare(bonusOrder);*/

            /*数据加入到redis存储，hashmap，key为cargoRecord，hashkey为网红的id，值就是总价格*/
            antherRedisMsg.saveCargoRecord(bonusOrder.getUser_id(),bonusOrder.getBonus_totalprice());

            if (result == 201){
                /*商品数量为0，清除redis中的缓存，更新数据库数据*/
                System.out.println("商品为0，清除所有数据");
                this.theSecondKillIsOver(bonusOrder.getWare_id(),bonusOrder.getBonus_id());
            }
        }

        return flag;
    }

    /*4.秒杀商品时间到了退出活动*/
    @Override
    public int theSecondKillIsOver(int ware_id, int bonus_id) {
        int flag = 0;
        /*对redis进行操作*/
        warePojo warePojo = bonusWareManager.clearAndReturnWareMessage(ware_id);
        List<standardsPojo> allStandard = bonusWareManager.getAllStandard(bonus_id);
        /*清除redis中的残留数据*/
        boolean l = bonusWareManager.clearBonus(bonus_id);
        bonusWareManager.clearStandardMessage(bonus_id);

        /*对数据库进行操作*/
        int i = bonusWareDao.closeWare(warePojo);
        int i1 = bonusWareDao.closeBonus(bonus_id);

        if(allStandard == null){
        }else {
            for (standardsPojo standard : allStandard) {
                /*根据规格数量，来判断该商品是否已经售罄，如果全部的数量都为0，那么该商品售罄*/
                bonusWareDao.closeStandard(standard.getStandards_number(), standard.getId());
                if (standard.getStandards_number() > 0) {
                    flag++;
                }
            }
        }

        /*判断该商品是否已经售罄了,售罄了就下架，没售罄就在售*/
        if (flag > 0){
            bonusWareDao.updateWareStateToSaleing(warePojo.getId());
        }else {
            bonusWareDao.updateWareStateToDone(warePojo.getId());
        }

        return i;
    }
}
