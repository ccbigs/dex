package com.dex.service;

import domain.busmanPojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:28
 * @message:
 */
public interface BusmanService {
    /*返回全部的商家信息*/
    List<busmanPojo> adminGetAllBusman() throws ParseException;

    /*管理员更新商家的状态*/
    int EditBusmanState(int id,String busman_state);
}
