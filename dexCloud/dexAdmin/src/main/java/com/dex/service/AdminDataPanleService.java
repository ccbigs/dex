package com.dex.service;

import dto.*;

import java.util.List;


/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:11:20
 * @message:
 */
public interface AdminDataPanleService {

    /*从redis获取上半部分所有的面板数据*/
    AdminPanleTopDTO GetAllPanleTop();

    /*管理员同步面板数据*/
    void PanelDataSynchronization();

    /*效益对比*/
    List<ATwoVolume> DexTwoPanleData();

    /*第一个面板的数据*/
    List<AOneVolume> DexOnePanleData();

    /*第三个面板数据*/
    AThreeVolume DexTherePanleData();
}
