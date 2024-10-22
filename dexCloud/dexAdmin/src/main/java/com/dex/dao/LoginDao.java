package com.dex.dao;

import domain.adminPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:14:36
 * @message:
 */
public interface LoginDao {
    /*-----------------管理员登陆-------------------------*/
    adminPojo adminLogin(String account);
}
