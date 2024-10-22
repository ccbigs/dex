package com.dexStore.service;

import domain.systemPojo;

/**
 * @author:左泽林
 * @date:日期:2021-10-09-时间:22:44
 * @message:
 */
public interface SystemService {

    /*获取当前启用的系统配置*/
    systemPojo getEnableSystem();

    /*更新常用的系统配置信息*/
    int updateUsuallySystem(systemPojo system);

    /*更改全部的配置信息*/
    int updateAllSystem(systemPojo system);

    /*获取dex商城电话*/
    String getDexPhone();

    /*获取热搜词语*/
    String getDexSearch();

    /*获取dex商城的logo*/
    String getDexLogo();
}
