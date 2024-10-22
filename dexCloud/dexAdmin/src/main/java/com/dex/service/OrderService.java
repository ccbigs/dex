package com.dex.service;

import domain.ordersPojo;
import org.apache.ibatis.annotations.Select;
import qo.AdminOrderPojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:00
 * @message:
 */
public interface OrderService {
    /*管理员获取全部的订单信息*/
    List<AdminOrderPojo> adminGetAllOrder() throws ParseException;

    /*管理员删除订单信息*/
    int adminDeleteOrder(int id);

}
