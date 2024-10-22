package com.dexUser.dao;

import domain.userPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:14:51
 * @message:
 */
@Repository
public interface UserDao {

    /*1-1用户更改自己的信息*/
    int updateUserInfo(userPojo user);

    /*1-2用户更改信息之后，传输新的信息*/
    userPojo getUpdateUserMessage(int user_id);

    /*用户修改密码*/
    int submitPass(userPojo user);

    /*更改身份证*/
    int submitIdCard(userPojo user);

    /*更改用户的邮箱*/
    int submitEmail(userPojo user);

    /*用户更改手机号*/
    int submitPhone(userPojo user);

    /*判断改手机号*/
    List<userPojo> phoneExit(String phone);
}
