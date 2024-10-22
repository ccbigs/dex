package com.dexUser.service;

import domain.userPojo;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:51
 * @message:
 */
public interface UserService {

    /*用户更改自己的信息*/
    JsonObject updateUserInfo(userPojo user);

    /*用户修改密码*/
    JsonObject submitPass(userPojo user);

    /*更改身份证*/
    JsonObject submitIdCard(userPojo user);

    /*更改用户的邮箱*/
    JsonObject submitEmail(userPojo user);

    /*用户更改手机号*/
    JsonObject submitPhone(userPojo user);

    /*获取用户信息*/
    userPojo getUserMessage(int user_id);
}
