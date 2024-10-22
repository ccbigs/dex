package com.dexBusman.serviceImpl;

import com.dexBusman.dao.LoginRegisterDao;
import com.dexBusman.manager.BusmanRedisMsg;
import com.dexBusman.service.LoginRegisterService;
import domain.busmanPojo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JsonObject;
import utils.getTimeNow;
import utils.jwtUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:18:16
 * @message:
 */
@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {

    @Resource
    private LoginRegisterDao loginRegisterDao;

    @Autowired
    private BusmanRedisMsg busmanRedisMsg;

    @Override
    public JsonObject busmanLogin(String account,String password) {
        JsonObject busLoginJson = new JsonObject();
        busmanPojo busmanPojo = loginRegisterDao.busmanLogin(account);
        if (busmanPojo == null){
            /*表示当前登陆的账号尚未注册*/
            busLoginJson.setCode(404);
        }else {
            if (password.equals(busmanPojo.getBus_password())){
                if ("正常".equals(busmanPojo.getBus_busman_state()) || "申请注销".equals(busmanPojo.getBus_busman_state())){
                    busLoginJson.setCode(200);
                    busLoginJson.setSize(1);
                    busLoginJson.setObject(busmanPojo);
                    busmanPojo.setBus_lasttime(new getTimeNow().getTimeNowYMDHMS());
                    loginRegisterDao.updateBusmanLoginTime(busmanPojo);
                    /*登陆成功，生成token*/
                    busLoginJson.setMessage(jwtUtils.generateToken(String.valueOf(busmanPojo.getBus_account())));
                    /*存入redis中,展现给管理员登录*/
                    busmanRedisMsg.saveBusmanLoginLog(String.valueOf(busmanPojo.getId()));
                }else {
                    /*密码错误*/
                    if ("审核中".equals(busmanPojo.getBus_busman_state())){
                        busLoginJson.setMessage("账号还在审核中，请您耐心等待");
                    }else {
                        busLoginJson.setMessage("该账号已被封禁，有问题请咨询DEX客服");
                    }
                    busLoginJson.setCode(501);
                }

            }else {
                busLoginJson.setCode(500);
            }
        }
        return busLoginJson;
    }

    @Override
    public JsonObject busmanRegister(busmanPojo busman) {
        JsonObject registerJson = new JsonObject();
        busman.setBus_busman_state("正常");
        busman.setBus_account(busman.getBus_phone());

        /*判断当前注册的手机号是不是已经被注册了*/
        if (loginRegisterDao.busmanExit(busman.getBus_phone()) == null){
            if (loginRegisterDao.busmanRegister(busman) > 0){
                registerJson.setCode(200);
            }else {
                registerJson.setCode(500);
            }

        }else {
            registerJson.setCode(501);
        }
        return registerJson;
    }
}
