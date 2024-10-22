package com.dex.service;

import domain.logPojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:18:10
 * @message:
 */
public interface LogService {
    /*管理员界面中管理员操作的记录日志*/
    void adminLog(logPojo logPojo);

    /*管理源获得全部日志*/
    List<logPojo> adminGetAllLog() throws ParseException;
}
