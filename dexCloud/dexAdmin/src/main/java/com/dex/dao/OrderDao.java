package com.dex.dao;

import domain.ordersPojo;
import org.apache.ibatis.annotations.Select;
import qo.AdminOrderPojo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:59
 * @message:
 */
public interface OrderDao {
    /*管理员获取全部的订单信息*/
    List<AdminOrderPojo> adminGetAllOrder();

    /*管理员删除订单信息*/
    int adminDeleteOrder(int id);

    /*获取全部的订单信息，转存到ES中*/
    @Select("select * from orders")
    List<ordersPojo> adminGetOrderToES();
}
