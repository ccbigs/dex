package com.dex.serviceImpl;


import com.dex.dao.AdminDataPanleDao;
import com.dex.manager.adminDataPanleMag;
import com.dex.service.AdminDataPanleService;
import domain.bonusOrderPojo;
import domain.ordersPojo;
import dto.*;

import org.springframework.stereotype.Service;
import utils.JudgeTime;
import utils.getTimeNow;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:11:21
 * @message:
 */
@Service
public class AdminDataPanleServiceImpl implements AdminDataPanleService {

    @Resource
    private AdminDataPanleDao adminDataPanleDao;

    @Resource
    private adminDataPanleMag adminDataPanleMag;

    @Override
    public AdminPanleTopDTO GetAllPanleTop() {
        return adminDataPanleMag.GetAllPanleTop();
    }

    /*管理员面板数据从数据库中拿取，存入redis中*/
    @Override
    public void PanelDataSynchronization() {
        AdminPanleTopDTO adminPanleTopDTO = new AdminPanleTopDTO();

        adminPanleTopDTO.setAllUser(adminDataPanleDao.getAllUser());
        adminPanleTopDTO.setAllBusman(adminDataPanleDao.getAllBusman());
        adminPanleTopDTO.setAllOrder(adminDataPanleDao.getAllOrder()+adminDataPanleDao.getAllBonusOrder());
        adminPanleTopDTO.setAllComment(adminDataPanleDao.getAllComment());
        /*bigdemical加*/
        adminPanleTopDTO.setAllSumTurnover(adminDataPanleDao.getAllSumTurnover().add(adminDataPanleDao.getAllbonusTotalPrice()));
        adminPanleTopDTO.setAllActuallyTurnover(adminDataPanleDao.getAllActuallyTurnover().add(adminDataPanleDao.getAllActuallyBonusTurnover()));

        adminPanleTopDTO.setLimitQutient(adminDataPanleDao.getLimitCarousel()+adminDataPanleDao.getLimitRecommend());
        adminPanleTopDTO.setAllQutientTurnover(adminDataPanleDao.AllQutientTurnover());

        /*存入redis*/
        adminDataPanleMag.PanelDataSynchronization(adminPanleTopDTO);

    }

    /*效益对比*/
    @Override
    public List<ATwoVolume> DexTwoPanleData() {
        List<ATwoVolume> twoList = new ArrayList();
        twoList.add(new ATwoVolume(adminDataPanleDao.getAllActuallyTurnover(),"实际普通购买"));
        twoList.add(new ATwoVolume(adminDataPanleDao.getAllActuallyBonusTurnover(),"实际秒杀效益"));
        twoList.add(new ATwoVolume(adminDataPanleDao.getAllSumTurnover(),"普通总销售"));
        twoList.add(new ATwoVolume(adminDataPanleDao.getAllbonusTotalPrice(),"秒杀总效益"));
        return twoList;
    }

    /*第一个面板的数据*/
    @Override
    public List<AOneVolume> DexOnePanleData() {
        AOneVolume aOneVolume = new AOneVolume();
        Integer[] VOrders={0,0,0,0,0,0,0};
        Integer[] VBonusOrders={0,0,0,0,0,0,0};
        BigDecimal[] VOrdersSale={new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
        BigDecimal[] VBonusOrdersSale={new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};;

        List<bonusOrderPojo> sevenDayBonusOrder = adminDataPanleDao.getSevenDayBonusOrder(getTimeNow.getDayAddYMS(1),getTimeNow.getDayAddYMS(-6));
        List<ordersPojo> sevenDayOrder = adminDataPanleDao.getSevenDayOrder(getTimeNow.getDayAddYMS(1), getTimeNow.getDayAddYMS(-6));

        try {
            /*秒杀*/
            for (bonusOrderPojo bonusOrder : sevenDayBonusOrder) {
                /**
                 * @author: 左先生
                 * @date: 2021-07-17 14:43
                 * @description:调用judge类的一个方法，来判断订单里面的日期到今天日期的时间，返回值为1或0- -6之间的数字
                 * 返回值为1，表示该类不存在于七天之前
                 */
                int index=JudgeTime.JudgeSevenDay(bonusOrder.getOrder_time())+6;
                if (index == 1){
                    continue;
                }else {
                    /*商品订单数量加一,商品的总销量加一*/
                    VBonusOrders[index]+=1;
                    VBonusOrdersSale[index]=VBonusOrdersSale[index].add(bonusOrder.getBonus_totalprice());
                }
            }
            /*普通购买*/
            for (ordersPojo order : sevenDayOrder) {
                /*下标为-6~0*/
                int index=JudgeTime.JudgeSevenDay(order.getOrders_date())+6;
                if (index == 1){
                    continue;
                }else {
                    /*普通订单数量加一*/
                    VOrders[index]+=1;
                    VOrdersSale[index]=VOrdersSale[index].add(order.getOrders_totalprice());
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        /*将数据加入进去*/
        aOneVolume.setBonusOrders(VBonusOrders);
        aOneVolume.setBonusOrdersSale(VBonusOrdersSale);
        aOneVolume.setOrders(VOrders);
        aOneVolume.setOrdersSale(VOrdersSale);

        return Arrays.asList(aOneVolume);
    }

    @Override
    /*第三个面板的数据*/
    public AThreeVolume DexTherePanleData(){
        AThreeVolume aThreeVolume = new AThreeVolume();
        /*
         * @author: 左先生
         * @date: 2021-09-07 10:09
         * @description:第三个面板数据是从redis中获取的，redis用hashmap来存储信息数据
        */
        Long[] userDate={0L,0L,0L,0L,0L,0L,0L};
        Long[] busmanDate={0L,0L,0L,0L,0L,0L,0L};

        for (int i = 0; i < 7; i++) {
            Long user = adminDataPanleMag.peopleActiveData(new StringBuffer("user").append(getTimeNow.getDayAddYMS(i-6)).toString());
            Long busman = adminDataPanleMag.peopleActiveData(new StringBuffer("busman").append(getTimeNow.getDayAddYMS(i-6)).toString());

            userDate[i] += user;
            busmanDate[i] += busman;
        }

        /*装填数据*/
        aThreeVolume.setUserDate(userDate);
        aThreeVolume.setBusmanDate(busmanDate);

        return aThreeVolume;
    }
}
