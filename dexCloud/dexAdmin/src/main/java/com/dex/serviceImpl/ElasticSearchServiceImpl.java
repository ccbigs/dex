package com.dex.serviceImpl;

import com.dex.dao.OrderDao;
import com.dex.manager.ESBonusOrderMag;
import com.dex.manager.ESOrdersMag;
import com.dex.manager.ESWareMag;
import com.dex.manager.adminDataPanleMag;
import com.dex.service.BonusService;
import com.dex.service.ElasticSearchService;
import com.dex.service.OrderService;
import com.dex.service.WareService;
import domain.bonusOrderPojo;
import domain.ordersPojo;
import domain.warePojo;
import dto.ESAdminMsgDTO;
import dto.ESBonusOrderDTO;
import dto.ESOrdersDTO;
import dto.ESWareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qo.AdminOrderPojo;
import utils.JsonObject;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-09-时间:11:50
 * @message:ES数据同步
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private WareService wareService;
    @Autowired
    private BonusService bonusService;
    @Resource
    private OrderDao orderDao;

    @Autowired
    private ESWareMag esWareMag;
    @Autowired
    private ESBonusOrderMag esBonusOrderMag;
    @Autowired
    private ESOrdersMag esOrdersMag;

    @Resource
    private adminDataPanleMag adminDataPanleMag;

    @Override
    public List<ESAdminMsgDTO> getNodeMessage() {
        return adminDataPanleMag.getNodeMessage();
    }

    @Override
    public int adminSynchronizationWareData(String account) throws ParseException {
        List<warePojo> warePojos = wareService.adminGetAllWare();

        /*先同步数据到ES*/
        for (warePojo ware : warePojos) {

            ESWareDTO esWareDTO = new ESWareDTO();
            esWareDTO.setId(ware.getId());
            esWareDTO.setBusman_id(ware.getBusman_id());
            esWareDTO.setWare_name(ware.getWare_name());
            esWareDTO.setWare_totalnumber(ware.getWare_totalnumber());
            esWareDTO.setWare_number(ware.getWare_number());
            esWareDTO.setWare_describe(ware.getWare_describe());
            esWareDTO.setWare_connect(ware.getWare_connect());
            esWareDTO.setWare_price(ware.getWare_price());
            esWareDTO.setWare_img1(ware.getWare_img1());
            esWareDTO.setWare_img2(ware.getWare_img2());
            esWareDTO.setWare_img3(ware.getWare_img3());
            esWareDTO.setWare_img4(ware.getWare_img4());
            esWareDTO.setWare_img5(ware.getWare_img5());
            esWareDTO.setWare_state(ware.getWare_state());
            esWareDTO.setWare_active(ware.getWare_active());
            esWareDTO.setWare_date(getTimeNow.CheckStringDateToDate(ware.getWare_date()));
            esWareDTO.setCategorie_name(ware.getCategorie_name());
            esWareDTO.setCategorie_id(ware.getCategorie_id());

            esWareDTO.setWare_activeDiscount(ware.getWare_activeDiscount());
            esWareDTO.setWare_activeFullReduction(ware.getWare_activeFullReduction());
            esWareDTO.setWare_activeFullPrice(ware.getWare_activeFullPrice());

            esWareDTO.setWare_bonus(ware.getWare_bonus());
            esWareDTO.setWare_bonusmoney(ware.getWare_bonusmoney());
            esWareMag.save(esWareDTO);
        }
        /*将ES中的索引长度同步到redis中*/
        long count = esWareMag.count();
        adminDataPanleMag.UpdateESNodeCount(0,count);

        /*记录日志*/

        if (warePojos.size() == count){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int adminSynchronizationBonusOrderData(String account) throws ParseException {
        List<bonusOrderPojo> bonusOrderPojos = bonusService.adminGetAllBonusOrder();

        /*同步数据*/
        for (bonusOrderPojo bonusOrder : bonusOrderPojos) {
            ESBonusOrderDTO esBonusOrderDTO = new ESBonusOrderDTO();

            esBonusOrderDTO.setId(bonusOrder.getId());
            esBonusOrderDTO.setBonus_id(bonusOrder.getBonus_id());
            esBonusOrderDTO.setUser_id(bonusOrder.getUser_id());
            esBonusOrderDTO.setBusman_id(bonusOrder.getBusman_id());
            esBonusOrderDTO.setWare_id(bonusOrder.getWare_id());
            esBonusOrderDTO.setStandard_id(bonusOrder.getStandard_id());
            esBonusOrderDTO.setBuy_number(bonusOrder.getBuy_number());
            esBonusOrderDTO.setBonus_totalprice(bonusOrder.getBonus_totalprice());
            esBonusOrderDTO.setAdd_name(bonusOrder.getAdd_name());
            esBonusOrderDTO.setAdd_phone(bonusOrder.getAdd_phone());
            esBonusOrderDTO.setAdd_local(bonusOrder.getAdd_local());
            esBonusOrderDTO.setAdd_postal(bonusOrder.getAdd_postal());
            esBonusOrderDTO.setOrder_time(getTimeNow.CheckStringDateToDate(bonusOrder.getOrder_time()));
            esBonusOrderDTO.setWare_bonusmoney(bonusOrder.getWare_bonusmoney());
            esBonusOrderDTO.setOrder_state(bonusOrder.getOrder_state());
            esBonusOrderDTO.setBusman_local(bonusOrder.getBusman_local());
            esBonusOrderDTO.setBusman_phone(bonusOrder.getBusman_phone());
            esBonusOrderDTO.setBusman_name(bonusOrder.getBusman_name());
            esBonusOrderDTO.setBonusorder_couriernumber(bonusOrder.getBonusorder_couriernumber());

            esBonusOrderMag.save(esBonusOrderDTO);
        }

        /*将ES中的索引长度同步到redis中*/
        long count = esBonusOrderMag.count();
        adminDataPanleMag.UpdateESNodeCount(1,count);

        if (bonusOrderPojos.size() == count){
            return 1;
        }else {
            return 0;
        }

    }

    @Override
    public int adminSynchronizationOrderData(String account) throws ParseException {
        List<ordersPojo> adminOrderPojos = orderDao.adminGetOrderToES();

        for (ordersPojo order : adminOrderPojos) {
            ESOrdersDTO esOrdersDTO = new ESOrdersDTO();

            esOrdersDTO.setId(order.getId());
            esOrdersDTO.setUser_id(order.getUser_id());
            esOrdersDTO.setBusman_id(order.getBusman_id());
            esOrdersDTO.setWare_id(order.getWare_id());
            esOrdersDTO.setAddress_id(order.getAddress_id());
            esOrdersDTO.setOrders_totalprice(order.getOrders_totalprice());
            esOrdersDTO.setOrders_number(order.getOrders_number());
            esOrdersDTO.setLogistics_status(order.getLogistics_status());
            esOrdersDTO.setOrders_date(getTimeNow.CheckStringDateToDate(order.getOrders_date()));
            esOrdersDTO.setActive_type(order.getActive_type());
            esOrdersDTO.setActive_detail(order.getActive_detail());
            esOrdersDTO.setActive_reduction(order.getActive_reduction());
            esOrdersDTO.setStandard_name(order.getStandard_name());
            esOrdersDTO.setUnit_price(order.getUnit_price());
            esOrdersDTO.setOrder_couriernumber(order.getOrder_couriernumber());

            esOrdersMag.save(esOrdersDTO);
        }

        /*将ES中的索引长度同步到redis中*/
        long count = esOrdersMag.count();
        adminDataPanleMag.UpdateESNodeCount(2,count);

        if (adminOrderPojos.size() == count){
            return 1;
        }else {
            return 0;
        }

    }
}
