package com.dexBusman.service;

import domain.busmanPojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author:左泽林
 * @date:日期:2021-10-13-时间:11:37
 * @message:
 */
public interface BusmanService {
    /*更改地址信息*/
    int updateLocal(busmanPojo busman);

    /*商家更改登陆密码*/
    int updatePassword(int id,String newPass);
}
