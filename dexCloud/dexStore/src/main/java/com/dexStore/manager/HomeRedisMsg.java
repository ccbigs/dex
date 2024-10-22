package com.dexStore.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:10:02
 * @message:
 */
public interface HomeRedisMsg {

    /*返回首页的第一个表单数据*/
    Map getOneData();


    /*返回对应日期的用户活跃数*/
    Long getUserDateActive(String UserDate);

    /*返回对应日期的商家活跃度*/
    Long getBusmanDateActive(String BusmanDate);

    /*返回对应日期的游客活跃度*/
    Long getTouristDateActive(String DateYMD);
}
