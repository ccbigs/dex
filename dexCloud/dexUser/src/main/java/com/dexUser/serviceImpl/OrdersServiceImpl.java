package com.dexUser.serviceImpl;

import com.dexUser.dao.OrdersDao;
import com.dexUser.manager.ESOrderMag;
import com.dexUser.service.OrdersService;
import domain.ordersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qo.order_add_warePojo;
import utils.JsonObject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:11:38
 * @message:
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Resource
    private OrdersDao ordersDao;




    /*商品详情页面的计算和购物车中的数据结算计算*/
    @Override
    public JsonObject setOrderForWareDetail(ordersPojo order) {
        JsonObject jsonObject = new JsonObject();

        if (ordersDao.setOrderForWareDetail(order) > 0){

            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }


    @Override
    public int cancelWare(int ware_id, int ware_number) {
        return ordersDao.cancelWare(ware_id, ware_number);
    }


    @Override
    public JsonObject getUserOrder(int user_id) {
        JsonObject o_a_wJson = new JsonObject();
        List<order_add_warePojo> o_a_ws = ordersDao.getUserOrder(user_id);

        if (o_a_ws == null){
            o_a_wJson.setCode(500);
        }else {
            o_a_wJson.setObject(o_a_ws);
            o_a_wJson.setSize(o_a_ws.size());
            o_a_wJson.setCode(200);
        }

        return o_a_wJson;
    }

    @Override
    public JsonObject getUserDidOrder(int user_id) {
        JsonObject didJson = new JsonObject();
        List<order_add_warePojo> userDidOrder = ordersDao.getUserDidOrder(user_id);

        if (userDidOrder == null) {
            didJson.setCode(500);
        }else {
            didJson.setCode(200);
            didJson.setSize(userDidOrder.size());
            didJson.setObject(userDidOrder);
        }

        return didJson;
    }

    /*取消订单*/
    @Override
    public JsonObject cancelOrder(order_add_warePojo oawp) {
        JsonObject cancelJson = new JsonObject();
        /*返回商品规格数量信息*/
        ordersDao.cancelStandard(oawp);
        if (ordersDao.cancelOrder(oawp.getId()) > 0){
            cancelJson.setCode(200);
        }else {
            cancelJson.setCode(500);
        }

        return cancelJson;
    }

    @Override
    public JsonObject getUserTransitOrder(int user_id) {
        List<order_add_warePojo> TransitOrder = ordersDao.getUserTransitOrder(user_id);
        JsonObject transitJson = new JsonObject();
        if (TransitOrder == null) {
            transitJson.setCode(500);
        }else {
            transitJson.setCode(200);
            transitJson.setObject(TransitOrder);
            transitJson.setSize(TransitOrder.size());
        }
        return transitJson;
    }

    @Override
    public JsonObject setTransitToDid(int order_id) {
        JsonObject ttdJson = new JsonObject();
        if (ordersDao.setTransitToDid(order_id) >0){
            ttdJson.setCode(200);
        }else {
            ttdJson.setCode(500);
        }
        return ttdJson;
    }

    @Override
    public JsonObject transitToReturn(int order_id) {
        JsonObject ttrJson = new JsonObject();

        if (ordersDao.transitToReturn(order_id) > 0){
            ttrJson.setCode(200);
        }else {
            ttrJson.setCode(500);
        }
        return ttrJson;
    }


    @Override
    public JsonObject getUserReturnOrder(int order_id) {
        JsonObject returnJson = new JsonObject();
        List<order_add_warePojo> userReturnOrder = ordersDao.getUserReturnOrder(order_id);

        if (userReturnOrder == null){
            returnJson.setCode(500);
        }else {
            returnJson.setCode(200);
            returnJson.setSize(userReturnOrder.size());
            returnJson.setObject(userReturnOrder);
        }

        return returnJson;

    }

}
