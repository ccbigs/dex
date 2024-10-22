package com.dexUser.service;

import domain.addressPojo;
import domain.bonusOrderPojo;
import utils.JsonObject;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:13
 * @message:
 */
public interface AddressService {


    /*添加地址*/
    JsonObject addUserAddress(addressPojo address);

    /*返回指定用户的地址*/
    JsonObject getUserAddress(int user_id);

    /*删除用户指定的地址*/
    JsonObject delUserAddress(int address_id);

    /*用户修改指定的地址*/
    JsonObject modifyAddress(addressPojo address);


}
