package com.dex.dao;

import domain.adminPojo;
import domain.logPojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:14:48
 * @message:
 */
public interface LogDao {

    /*管理员登录时间更新*/
    void adminUpdateLastTime(adminPojo admin);

    /*管理员日志行为插入*/
    void adminLog(logPojo log);

    /*管理源获得全部日志*/
    List<logPojo> adminGetAllLog();
}
