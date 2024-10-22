package com.dexUser.serviceImpl;

import com.dexUser.dao.UserDao;
import com.dexUser.service.UserService;
import domain.userPojo;
import org.springframework.stereotype.Service;
import utils.JsonObject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:51
 * @message:
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public JsonObject updateUserInfo(userPojo user) {
        JsonObject uuiJson = new JsonObject();
        System.out.println(user.toString());
        if(userDao.updateUserInfo(user) > 0){
            uuiJson.setObject(userDao.getUpdateUserMessage(user.getId()));
            uuiJson.setCode(200);
        }else {

            uuiJson.setCode(500);
        }

        return uuiJson;
    }

    @Override
    public JsonObject submitPass(userPojo user) {
        JsonObject jsonObject = new JsonObject();
        if(userDao.submitPass(user) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    @Override
    public JsonObject submitIdCard(userPojo user) {
        JsonObject jsonObject = new JsonObject();
        if(userDao.submitIdCard(user) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    @Override
    public JsonObject submitEmail(userPojo user) {
        JsonObject jsonObject = new JsonObject();
        if(userDao.submitEmail(user) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }
        return jsonObject;
    }

    /*更改手机号，更改手机号的同时也会更改账号*/
    @Override
    public JsonObject submitPhone(userPojo user) {
        JsonObject jsonObject = new JsonObject();
        List<userPojo> userPojos = userDao.phoneExit(user.getPhone());
        /*先判断用户更改的手机号是否存在*/
        if (userPojos.size() == 0){
            if(userDao.submitPhone(user) > 0){
                System.out.println("进入111111");
                jsonObject.setCode(200);
            }else {
                System.out.println("进入222222");
                jsonObject.setCode(500);
            }
        }else {
            System.out.println("进入333333");
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*获取指定用户id的用户信息*/
    @Override
    public userPojo getUserMessage(int user_id) {
        return userDao.getUpdateUserMessage(user_id);
    }
}
