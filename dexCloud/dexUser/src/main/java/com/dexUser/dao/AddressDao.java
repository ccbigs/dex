package com.dexUser.dao;

import domain.addressPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:12
 * @message:
 */
@Repository
public interface AddressDao {

    /*添加地址*/
    int addUserAddress(addressPojo address);

    /*返回指定用户的地址*/
    List<addressPojo> getUserAddress(int user_id);

    /*删除用户指定的地址*/
    int delUserAddress(int address_id);

    /*用户修改指定的地址*/
    int modifyAddress(addressPojo address);

}
