package com.dex.controller;

import com.dex.service.LogService;
import com.dex.util.GetIP;
import domain.adminPojo;
import domain.logPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.web.bind.annotation.*;
import utils.JsonObject;
import utils.getTimeNow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:18:10
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class LogController {
    @Autowired
    private LogService logService;

    @RequestMapping("/adminLog")
    void adminLog(String behavior, HttpServletRequest req){
        logPojo log = new logPojo();
        adminPojo admin = (adminPojo) req.getSession().getAttribute("admin");

        log.setUser_id(admin.getId());
        log.setUser(admin.getAccount());
        log.setBehavior(admin.getAccount()+behavior);
        log.setInternetProtocol(GetIP.getIP(req));
        log.setTime(new getTimeNow().getTimeNowYMDHMS());
        log.setUser_type("管理员");

        logService.adminLog(log);
    }

    @PostMapping("/adminGetAllLog")
    JsonObject adminGetAllLog() throws ParseException {
        List<logPojo> logPojos = logService.adminGetAllLog();
        JsonObject jsonObject = new JsonObject();

        if (logPojos.size() == 0){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setSize(logPojos.size());
            jsonObject.setObject(logPojos);
        }

        return jsonObject;
    }
}
