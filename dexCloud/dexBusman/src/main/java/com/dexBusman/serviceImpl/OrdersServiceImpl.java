package com.dexBusman.serviceImpl;

import com.dexBusman.dao.OrdersDao;
import com.dexBusman.manager.ESOrderMag;
import com.dexBusman.manager.ESWareMag;
import com.dexBusman.service.OrdersService;
import domain.addressPojo;
import dto.ESOrdersDTO;
import dto.ESWareDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qo.BonusOrderWarePojo;
import qo.order_add_warePojo;
import utils.JsonObject;
import utils.ObjectChangeUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:52
 * @message:
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private OrdersDao orderdao;

    @Autowired
    private ESOrderMag esOrderMag;

    @Autowired
    private ESWareMag esWareMag;

    @Override
    public JsonObject busmanGetOrders(int busman_id) {
        List<order_add_warePojo> order_add_warePojos = orderdao.busmanGetOrders(busman_id);
        JsonObject getOrderJson = new JsonObject();
        if (order_add_warePojos == null){
            getOrderJson.setCode(500);
        }else {
            getOrderJson.setCode(200);
            getOrderJson.setSize(order_add_warePojos.size());
            getOrderJson.setObject(order_add_warePojos);
        }
        return getOrderJson;
    }

    @Override
    public JsonObject busmanSendWare(int id,String expressNumber) {
        JsonObject sendJson = new JsonObject();
        if (orderdao.busmanSendWare(String.valueOf(id),expressNumber) > 0){
            sendJson.setCode(200);
        }else {
            sendJson.setCode(500);
        }
        return sendJson;
    }

    @Override
    public JsonObject busmanSignWare(int id) {
        JsonObject bswJson = new JsonObject();
        if (orderdao.busmanSignWare(id) > 0){
            bswJson.setCode(200);
        }else {
            bswJson.setCode(500);
        }
        return bswJson;
    }

    @Override
    public JsonObject busmanSearchOrder(int busman_id,int order_id) {
        JsonObject searchJson = new JsonObject();
        order_add_warePojo order_add_warePojo = orderdao.busmanSearchOrder(order_id);
        if (order_add_warePojo == null){
            searchJson.setCode(404);
        }else{
            if (order_add_warePojo.getBusman_id() == busman_id){
                searchJson.setCode(200);
                searchJson.setObject(order_add_warePojo);
                searchJson.setSize(1);
            }else {
                searchJson.setCode(500);
            }
        }
        return searchJson;
    }

    @Override
    public order_add_warePojo busmanSearchOrderByES(int busman_id, int order_id) {

        ESOrdersDTO esOrdersDTO = esOrderMag.findById((long) order_id).get();

        if (esOrdersDTO != null && busman_id == esOrdersDTO.getBusman_id()){

            ESWareDTO esWareDTO = esWareMag.findById((long)esOrdersDTO.getWare_id()).get();
            addressPojo addressByAddress_id = orderdao.getAddressByAddress_id(esOrdersDTO.getAddress_id());

            return ObjectChangeUtils.SearchChangeESToQu(esOrdersDTO,esWareDTO,addressByAddress_id);
        }else {

            return null;
        }
    }

    /*商家搜索数据库中的秒杀订单信息*/
    @Override
    public BonusOrderWarePojo busmanSearchBonusOrder(int busman_id, int order_id) {
        BonusOrderWarePojo bonusOrderWarePojo = orderdao.busmanSearchBonusOrder(busman_id, order_id);
        if ("备货中".equals(bonusOrderWarePojo.getOrder_state())){
            bonusOrderWarePojo.setBonusorder_couriernumber("商品正在积极备货中");
        }

        return bonusOrderWarePojo;
    }
}
