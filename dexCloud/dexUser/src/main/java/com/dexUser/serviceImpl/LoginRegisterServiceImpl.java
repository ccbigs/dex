package com.dexUser.serviceImpl;

import com.dexUser.dao.LoginRegisterDao;
import com.dexUser.manager.RedisManager;
import com.dexUser.service.LoginRegisterService;
import domain.userPojo;
import org.springframework.stereotype.Service;
import utils.JsonObject;
import utils.jwtUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:10:46
 * @message:
 */
@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {

    @Resource
    private LoginRegisterDao loginRegisterDao;

    @Resource
    private RedisManager redisManager;

    @Override
    public JsonObject login(String account, String password, HttpServletRequest req) {
        userPojo user = loginRegisterDao.login(account);
        JsonObject userJson = new JsonObject();
        /*判断该用户是否存在*/
        if (user == null) {
            userJson.setCode(404);
            return userJson;
        }else if("封禁".equals(user.getUser_state())){
            userJson.setCode(501);
            return userJson;
        }else{
            /*判断该用户的密码是否正确*/
            if (user.getPassword().equals(password)) {
                userJson.setObject(user);
                userJson.setCode(200);
                userJson.setSize(1);
                /*返回该用户的token*/
                userJson.setMessage(jwtUtils.generateToken(String.valueOf(user.getAccount())));
                /*将用户信息添加进入session中*/
                req.getSession().setAttribute(String.valueOf(user.getId()),user);
                /*存入redis*/
                redisManager.saveUserLoginLog(String.valueOf(user.getId()));
                return userJson;
            }
        }
        userJson.setCode(500);
        return userJson;
    }

    @Override
    public JsonObject register(userPojo user) {
        JsonObject registerJson = new JsonObject();
        /*判断数据库的返回信息，返回用户是否注册成功*/

        if (loginRegisterDao.phoneHas(user.getPhone()) > 0){
            registerJson.setCode(500);
            registerJson.setMessage("当前号码已被注册");
        }else {
            if(loginRegisterDao.register(user) > 0){
                registerJson.setCode(200);
            }else {
                registerJson.setCode(500);
                registerJson.setMessage("用户注册失败");
            }
        }
        return registerJson;
    }



}
