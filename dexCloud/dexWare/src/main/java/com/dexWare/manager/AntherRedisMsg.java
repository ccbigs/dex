package com.dexWare.manager;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:10:49
 * @message:
 */
public interface AntherRedisMsg {

    /*存储网红带货数据*/
    void saveCargoRecord(int id, BigDecimal totalPrice);

    /*存放打开秒杀页面的游客数*/
    void savePeopleActive();

}
