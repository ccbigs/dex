package com.dexUser.serviceImpl;

import com.dexUser.dao.AddressDao;
import com.dexUser.service.AddressService;
import domain.addressPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JsonObject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:13
 * @message:
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressdao;

    @Override
    public JsonObject addUserAddress(addressPojo address) {
        JsonObject addJson = new JsonObject();
        if (addressdao.addUserAddress(address) > 0){
            addJson.setCode(200);
        }else {
            addJson.setCode(500);
        }
        return addJson;
    }

    @Override
    public JsonObject getUserAddress(int user_id) {
        List<addressPojo> userAddress = addressdao.getUserAddress(user_id);
        JsonObject addressJson = new JsonObject();
        if (userAddress == null){
            addressJson.setCode(500);
        }else {
            addressJson.setCode(200);
            addressJson.setObject(userAddress);
            addressJson.setSize(userAddress.size());
        }

        return addressJson;
    }

    @Override
    public JsonObject delUserAddress(int address_id) {
        JsonObject delJson = new JsonObject();
        if (addressdao.delUserAddress(address_id) > 0){
            delJson.setCode(200);
        }else {
            delJson.setCode(500);
        }
        return delJson;
    }

    @Override
    public JsonObject modifyAddress(addressPojo address) {
        JsonObject modifyJson = new JsonObject();
        if (addressdao.modifyAddress(address) > 0 ){
            modifyJson.setCode(200);
        }else {
            modifyJson.setCode(500);
        }
        return modifyJson;
    }
}
