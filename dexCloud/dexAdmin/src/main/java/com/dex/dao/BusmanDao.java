package com.dex.dao;

import domain.busmanPojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:28
 * @message:
 */
public interface BusmanDao {
    /*返回全部的商家信息*/
    List<busmanPojo> adminGetAllBusman();

    /*管理员更新商家的状态*/
    int EditBusmanState(busmanPojo busman);

    /*管理员删除商家账号*/
    int deleteBusman(int id);
}
