package com.dex.dao;

import domain.userPojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:36
 * @message:
 */
public interface UserDao {
    /*管理员获得全部的用户信息*/
    List<userPojo> adminGetAllUser();

    /*商家修改用户的信息*/
    int adminModifyUserState(userPojo user);

    /*管理员删除指定用户*/
    int adminDeleteUser(int id);
}
