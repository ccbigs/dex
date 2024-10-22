package com.dexBusman.serviceImpl;

import com.dexBusman.dao.ActiveDao;
import com.dexBusman.service.ActiveService;
import domain.activePojo;
import domain.warePojo;
import org.springframework.stereotype.Service;
import utils.JsonObject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:17:38
 * @message:
 */
@Service
public class ActiveServiceImpl implements ActiveService {

    @Resource
    private ActiveDao activedao;

    /*----------------------商家活动-----------------------*/
    @Override
    public JsonObject busmanNoActive(int busman_id) {
        JsonObject noActiveJson = new JsonObject();
        List<warePojo> warePojos = activedao.busmanNoActive(busman_id);
        if (warePojos.size() > 0 ){
            noActiveJson.setCode(200);
            noActiveJson.setSize(warePojos.size());
            noActiveJson.setObject(warePojos);
        }else {
            noActiveJson.setCode(500);
        }
        return noActiveJson;
    }

    @Override
    public JsonObject getActiveDidWare(int busman_id) {
        JsonObject didJson = new JsonObject();
        List<warePojo> activeDidWare = activedao.getActiveDidWare(busman_id);
        if (activeDidWare == null){
            didJson.setCode(500);
        }else {
            didJson.setCode(200);
            didJson.setObject(activeDidWare);
            didJson.setSize(activeDidWare.size());
        }
        return didJson;
    }

  /*  @Override
    public JsonObject ModifyWareActive(String ware_active,String ware_id) {
        JsonObject mwaJson = new JsonObject();

        if (activedao.ModifyWareActive(ware_active, ware_id) > 0) {
            mwaJson.setCode(200);
        }else {
            mwaJson.setCode(500);
        }
        return mwaJson;
    }
*/
    @Override
    public JsonObject busmanAddFullReduction(activePojo active) {
        JsonObject addJson = new JsonObject();
        if (activedao.busmanAddFullReduction(active) > 0){
            addJson.setCode(200);
        }else {
            addJson.setCode(500);
        }
        return addJson;
    }

    @Override
    public JsonObject busmanAddDiscount(activePojo active) {
        JsonObject discountJson = new JsonObject();
        if (activedao.busmanAddDiscount(active) > 0 ){
            discountJson.setCode(200);
        }else {
            discountJson.setCode(500);
        }
        return discountJson;
    }

    @Override
    public JsonObject outActive(int busman_id, int ware_id) {
        JsonObject outJson = new JsonObject();

        if (activedao.outActiveWare(busman_id, ware_id) > 0){
            outJson.setCode(200);
        }else {
            outJson.setCode(500);
        }

        return outJson;
    }

    @Override
    public JsonObject getWareActiveMessage(int ware_id, int busman_id) {
        JsonObject MessageJson = new JsonObject();
        warePojo wareActiveMessage = activedao.getWareActiveMessage(ware_id, busman_id);
        if (wareActiveMessage == null){
            MessageJson.setCode(500);
        }else {
            MessageJson.setCode(200);
            MessageJson.setSize(1);
            MessageJson.setObject(wareActiveMessage);
        }
        return MessageJson;
    }

    @Override
    public JsonObject busmanUpdateDiscount(warePojo active) {
        JsonObject upDisJson = new JsonObject();
        if (activedao.busmanUpdateDiscount(active) > 0){
            upDisJson.setCode(200);
        }else {
            upDisJson.setCode(500);
        }
        return upDisJson;
    }

    @Override
    public JsonObject busmanUpdateFull(warePojo active) {
        JsonObject upFullJson = new JsonObject();
        if (activedao.busmanUpdateFull(active) > 0){
            upFullJson.setCode(200);
        }else {
            upFullJson.setCode(500);
        }
        return upFullJson;
    }
}
