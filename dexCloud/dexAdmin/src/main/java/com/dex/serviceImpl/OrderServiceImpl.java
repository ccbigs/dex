package com.dex.serviceImpl;

import com.dex.dao.OrderDao;
import com.dex.service.OrderService;
import domain.ordersPojo;
import org.springframework.stereotype.Service;
import qo.AdminOrderPojo;
import utils.getTimeNow;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:16:00
 * @message:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Override
    public List<AdminOrderPojo> adminGetAllOrder() throws ParseException {
        List<AdminOrderPojo> AdminOrderPojos = orderDao.adminGetAllOrder();

        for (AdminOrderPojo OAP : AdminOrderPojos) {
            OAP.setOrders_date(getTimeNow.dataBasicToYMDHMS(OAP.getOrders_date()));
        }

        return AdminOrderPojos;
    }

    @Override
    public int adminDeleteOrder(int id) {
        return orderDao.adminDeleteOrder(id);
    }


}
