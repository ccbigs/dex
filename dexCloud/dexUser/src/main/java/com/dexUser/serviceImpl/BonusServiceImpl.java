package com.dexUser.serviceImpl;

import com.dexUser.dao.BonusDao;
import com.dexUser.manager.RedisManager;
import com.dexUser.service.BonusService;
import domain.bonusOrderPojo;
import domain.bonusPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qo.BonusBonusOrderPojo;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-14-时间:11:03
 * @message:
 */
@Service
public class BonusServiceImpl implements BonusService {

    @Resource
    private BonusDao bonusDao;

    @Resource
    private RedisManager redisManager;

    @Override
    public int userAttendBonusByWare(bonusPojo bonus) {
        if (bonusDao.userAttendBonusByWare(bonus) > 0){
            return bonus.getId();
        }else {
            return 500;
        }
    }

    /*1.参与商品直播成功，编写redis缓存业务*/
    @Transactional
    @Override
    public int successfulParticipationInSecondKill(int ware_id, int bonus_id) {
        int flag = 200;
        /*1.1 先更改商品的状态，改成秒杀中*/
            /*先创建悲观锁*/
        warePojo warePojo = bonusDao.warePessimisticLock(ware_id);
        if (bonusDao.updateWareState(warePojo.getId()) > 0){
            /*提取出商品信息存入redis*/
            redisManager.depositWare(bonusDao.getBonusWareMessage(ware_id));
            /*提取bonus信息存入Redis中*/
            redisManager.depositBonus(bonusDao.getBonusByWare(ware_id, bonus_id));
            /*提取商品规格信息，存入Redis中*/
            redisManager.depositStandards(bonusDao.getBonusByStandards(ware_id),bonus_id);
        }else {
            flag = 500;
        }
        return flag;
    }

    /*用户请求秒杀结果数据，返回直播数据和分红*/
    @Override
    public List<BonusBonusOrderPojo> getUserBonusDate(int user_id) throws ParseException {
        List<BonusBonusOrderPojo> userBonusDate = bonusDao.getUserBonusDate(user_id);

        for (BonusBonusOrderPojo bonusBonusOrderPojo : userBonusDate) {
            bonusBonusOrderPojo.setBegindate(getTimeNow.dataBasicToYMDHMS(bonusBonusOrderPojo.getBegindate()));
            bonusBonusOrderPojo.setEnddate(getTimeNow.dataBasicToYMDHMS(bonusBonusOrderPojo.getEnddate()));
        }

        return userBonusDate;
    }

    /*计算用户的秒杀收益*/
    @Override
    public BigDecimal[] getUserProfit(int user_id) {
        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.ZERO,BigDecimal.ZERO};
        List<bonusOrderPojo> userProfit = bonusDao.getUserProfit(user_id);
        System.out.println(userProfit.toString());
        for (bonusOrderPojo profit : userProfit) {
            if ("备货中".equals(profit.getOrder_state())){
                bigDecimals[0]=bigDecimals[0].add(profit.getWare_bonusmoney());
            }else {
                bigDecimals[1]=bigDecimals[1].add(profit.getWare_bonusmoney());
            }
        }

        bigDecimals[0]=bigDecimals[0].add(bigDecimals[1]);

        System.out.println(bigDecimals[0]+","+bigDecimals[1]);

        return bigDecimals;
    }
}
