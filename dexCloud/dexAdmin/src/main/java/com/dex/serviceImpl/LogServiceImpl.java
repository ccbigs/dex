package com.dex.serviceImpl;

import com.dex.dao.LogDao;
import com.dex.service.LogService;
import domain.logPojo;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:18:10
 * @message:
 */
@Service
public class LogServiceImpl  implements LogService {

    @Resource
    private LogDao logDao;

    @Override
    public void adminLog(logPojo logPojo) {
        System.out.println("后台"+logPojo.toString());
        logDao.adminLog(logPojo);
    }

    /*管理员获取全部日志信息*/
    @Override
    public List<logPojo> adminGetAllLog() throws ParseException {
        List<logPojo> logPojos = logDao.adminGetAllLog();

        for (logPojo log : logPojos) {
            log.setTime(getTimeNow.dataBasicToYMDHMS(log.getTime()));
        }

        return logPojos;
    }
}
