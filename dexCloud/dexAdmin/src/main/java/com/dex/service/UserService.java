package com.dex.service;

import domain.userPojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:37
 * @message:
 */
public interface UserService {
    /*管理员获得全部的用户信息*/
    List<userPojo> adminGetAllUser();

    /*商家修改用户的信息*/
    int adminModifyUserState(int id,String state);

    /*管理员删除指定用户*/
    int adminDeleteUser(int id);
}
