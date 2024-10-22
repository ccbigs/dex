package utils;

import domain.addressPojo;
import dto.ESOrdersDTO;
import dto.ESWareDTO;
import qo.order_add_warePojo;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-09-10-时间:12:02
 * @message:
 */
public class ObjectChangeUtils {

    public static order_add_warePojo SearchChangeESToQu(ESOrdersDTO esOrdersDTO, ESWareDTO esWareDTO, addressPojo address){
        order_add_warePojo OAW = new order_add_warePojo();

        OAW.setId(esOrdersDTO.getId());
        OAW.setUser_id(esOrdersDTO.getUser_id());
        OAW.setBusman_id(esOrdersDTO.getBusman_id());
        OAW.setWare_id(esOrdersDTO.getWare_id());
        OAW.setAddress_id(esOrdersDTO.getAddress_id());
        OAW.setOrders_totalprice(esOrdersDTO.getOrders_totalprice().setScale(2,BigDecimal.ROUND_CEILING).doubleValue());
        OAW.setOrders_number(esOrdersDTO.getOrders_number());
        OAW.setLogistics_status(esOrdersDTO.getLogistics_status());
        OAW.setOrders_date(getTimeNow.DateToStringYMDHMS(esOrdersDTO.getOrders_date()));
        OAW.setOrder_couriernumber(esOrdersDTO.getOrder_couriernumber());
        OAW.setLocal(address.getLocal());
        OAW.setDetail(address.getDetail());
        OAW.setPhone(address.getPhone());
        OAW.setName(address.getName());
        OAW.setPostal(address.getPostal());
        OAW.setWare_name(esWareDTO.getWare_name());
        OAW.setWare_price(esWareDTO.getWare_price());
        OAW.setWare_img1(esWareDTO.getWare_img1());

        return OAW;

    }
}
