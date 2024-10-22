package com.dexWare.serviceImpl;

import com.dexWare.service.DetailWareService;
import com.dexWare.dao.DetailWareDao;
import domain.activePojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:26
 * @message:
 */
@Service
public class DetailWareServiceImpl implements DetailWareService {

    @Autowired
    private DetailWareDao detailWareDao;

    @Override
    public warePojo getWareById(int wareId) {
        return detailWareDao.getWareById(wareId);
    }

    @Override
    public activePojo getWareActive(int ware_id) {
        return detailWareDao.getWareActive(ware_id);
    }

    @Override
    public JsonObject reduceWareForWareDetail(int ware_id, int num) {
        JsonObject jsonObject = new JsonObject();

        if (detailWareDao.reduceWareForWareDetail(ware_id, num) > 0 ){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*返回商家的地址信息*/
    @Override
    public String getBusmanAddress(int ware_id) {
        return detailWareDao.getBusmanAddress(ware_id);
    }
}
