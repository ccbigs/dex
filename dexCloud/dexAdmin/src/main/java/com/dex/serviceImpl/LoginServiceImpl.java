package com.dex.serviceImpl;

import com.dex.dao.LogDao;
import com.dex.dao.LoginDao;
import com.dex.manager.adminDataPanleMag;
import com.dex.service.LoginService;
import com.dex.util.GetIP;
import domain.adminPojo;
import domain.logPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JsonObject;
import utils.getTimeNow;
import utils.jwtUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:14:36
 * @message:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

    @Resource
    private LogDao logDao;

    @Autowired
    private adminDataPanleMag adminDataPanleMag;


    /*管理员登录，成功完成redis存储管理员信息，以及返回token*/
    @Override
    public JsonObject adminLogin(HttpServletRequest req,String account, String password) {
        adminPojo admin = loginDao.adminLogin(account);
        JsonObject jsonObject = new JsonObject();
        HttpSession session = req.getSession();

        /*判断管理员账号密码以及状态*/
        if (admin == null){
            jsonObject.setCode(404);
        }else if ("禁用".equals(admin.getAdmin_state())){
            jsonObject.setCode(401);
        }else {
            if (password.equals(admin.getPassword())){
                jsonObject.setCode(200);
                jsonObject.setSize(1);
                jsonObject.setObject(admin);
                session.setAttribute("admin",admin);
                admin.setLast_time(new getTimeNow().getTimeNowYMDHMS());
                this.logAdminLogin(GetIP.getIP(req),admin.getId(),admin.getAccount());
                logDao.adminUpdateLastTime(admin);
                /*将管理员的信息存入redis，同时将生成的token放进返回的方法体中*/
                adminDataPanleMag.saveAdminMessage(admin);
                /*将生成的token存放在message中*/
                jsonObject.setMessage(jwtUtils.generateToken("admin-"+admin.getAccount()));
            }else {
                jsonObject.setCode(500);
            }
        }
        return jsonObject;
    }


    /*管理员登录日志*/
    void logAdminLogin(String ip,int user_id,String account){
        logPojo log = new logPojo();

        log.setUser_id(user_id);
        log.setUser(account);
        log.setBehavior(account+"管理员登录");
        log.setInternetProtocol(ip);
        log.setTime(new getTimeNow().getTimeNowYMDHMS());
        log.setUser_type("管理员");

        logDao.adminLog(log);
    }
}
