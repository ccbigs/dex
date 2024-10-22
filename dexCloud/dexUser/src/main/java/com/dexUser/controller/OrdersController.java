package com.dexUser.controller;

import com.dexUser.service.OrdersService;
import domain.cartPojo;
import domain.ordersPojo;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qo.order_add_warePojo;
import utils.JsonObject;
import utils.getTimeNow;
import vo.CartVo;
import vo.WareVo;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:11:38
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;



    /*
     * @author: 左先生
     * @date: 2021-07-23 9:49
     * @description:由于前端的数据量不够用，所以传过来的数据中ware.getWare_img3()表示用户购买的数量，ware.getWare_img4()是根据ware.getWare_active()；来决定的
     * ware.getWare_img4()可以表示打折的详情或者表示满减需要满足的金额，ware.getWare_img5()表示满减信息中减的金额
     */
    @RequestMapping("/setOrderForWareDetail")
    JsonObject setOrderForWareDetail(WareVo ware){
        ordersPojo order = new ordersPojo();

        if ("满减".equals(ware.getWare_active())){
            if (BigDecimal.valueOf(ware.getWare_price()*ware.getBuy_numbers()).compareTo(ware.getWare_activeFullPrice()) < 0 ){
                order.setOrders_totalprice(BigDecimal.valueOf(ware.getWare_price()*ware.getBuy_numbers()));
            }else {
                /*满足满减的条件*/
                order.setOrders_totalprice(BigDecimal.valueOf(ware.getWare_price()*ware.getBuy_numbers()).subtract(ware.getWare_activeFullReduction()));
                order.setActive_reduction(ware.getWare_activeFullReduction().doubleValue());
            }
        }else if("打折".equals(ware.getWare_active())){
            order.setOrders_totalprice( BigDecimal.valueOf(0.1*ware.getWare_activeDiscount()*ware.getWare_price()*ware.getBuy_numbers()));
            order.setActive_detail(ware.getWare_activeDiscount());
        }else {
            order.setOrders_totalprice(BigDecimal.valueOf(ware.getWare_price()*ware.getBuy_numbers()));
        }


        order.setUser_id(ware.getUser_id());
        order.setBusman_id(ware.getBusman_id());
        order.setWare_id(ware.getId());
        order.setAddress_id(ware.getAddress_id());
        order.setOrders_number(ware.getBuy_numbers());
        order.setLogistics_status("已下单");
        order.setOrders_date(new getTimeNow().getTimeNowYMDHMS());
        order.setActive_type(ware.getWare_active());
        /*新增商品规格信息*/
        order.setStandard_name(ware.getStandard_name());
        order.setUnit_price(new BigDecimal(ware.getWare_price()));

        return ordersService.setOrderForWareDetail(order);
    }

    /*点击购物车的结算,业务逻辑代码引用商品详情页结算的代码*/
    @RequestMapping("/setOrderForShopCart")
    JsonObject setOrderForShopCart(cartPojo cartWare){
        System.out.println(cartWare);

        ordersPojo order = new ordersPojo();
        order.setUser_id(cartWare.getUser_id());
        order.setBusman_id(cartWare.getBusman_id());
        order.setWare_id(cartWare.getWare_id());
        order.setAddress_id(Integer.parseInt(cartWare.getWare_connect()));
        order.setOrders_number(cartWare.getCart_number());
        order.setLogistics_status("已下单");
        order.setOrders_date(new getTimeNow().getTimeNowYMDHMS());

        /*判断商品的类型*/
        if ("满减".equals(cartWare.getWare_active())){
            /*如果购买的数量的总价格大于满减价格，就触发满减,if比较如果满减价格小于实际价格，不参与满减*/
            if (cartWare.getWare_activeFullPrice().compareTo(BigDecimal.valueOf(cartWare.getCart_number()*cartWare.getWare_price() )) < 0){
                order.setOrders_totalprice(BigDecimal.valueOf(cartWare.getWare_price()*cartWare.getCart_number()));
            }else {
                /*使用bigdecimal中的相减的方法*/
                order.setOrders_totalprice(BigDecimal.valueOf(cartWare.getCart_number()*cartWare.getWare_price()).subtract(cartWare.getWare_activeFullReduction()));
                order.setActive_reduction(cartWare.getWare_activeFullReduction().doubleValue());
                order.setActive_detail(cartWare.getWare_activeFullPrice().doubleValue());
            }
        }else if ("打折".equals(cartWare.getWare_active())){
            /*打折的信息*/
            order.setOrders_totalprice(BigDecimal.valueOf(cartWare.getCart_number()*cartWare.getWare_price()*cartWare.getWare_activeDiscount()*0.1));
            order.setActive_detail(cartWare.getWare_activeDiscount());
        }else {
            order.setOrders_totalprice(BigDecimal.valueOf(cartWare.getWare_price()*cartWare.getCart_number()));
        }

        /*状态商品的活动和商品的规格名称*/
        order.setActive_type(cartWare.getWare_active());
        order.setStandard_name(cartWare.getStandards_name());
        order.setUnit_price(new BigDecimal(cartWare.getWare_price()));

        /*下订单后的结果*/
        return  ordersService.setOrderForWareDetail(order);

    }



    @RequestMapping("/getUserOrder")
    JsonObject getUserOrder(int user_id){
        return ordersService.getUserOrder(user_id);
    }

    @RequestMapping("/getUserDidOrder")
    JsonObject getUserDidOrder(int user_id){
        return ordersService.getUserDidOrder(user_id);
    }

    /*获取运输中的订单*/
    @RequestMapping("/getUserTransitOrder")
    JsonObject getUserTransitOrder(int user_id){
        return ordersService.getUserTransitOrder(user_id);
    }

    /*返沪全部退换货信息*/
    @RequestMapping("/getUserReturnOrder")
    JsonObject getUserReturnOrder(int user_id){
        return ordersService.getUserReturnOrder(user_id);
    }

    /*取消订单*/
    @RequestMapping("/cancelOrder")
    JsonObject cancelOrder(order_add_warePojo oawp){
        /*返回商品ware的原数量*/
        ordersService.cancelWare(oawp.getWare_id(),oawp.getOrders_number());
        /*商品规格数量的返回在cancelOrder中*/
        return ordersService.cancelOrder(oawp);
    }

    /*签收订单*/
    @RequestMapping("/setTransitToDid")
    JsonObject setTransitToDid(int order_id){
        return ordersService.setTransitToDid(order_id);
    }

    /*运输中的订单改换为退换货*/
    @RequestMapping("/transitToReturn")
    JsonObject transitToReturn(int order_id){
        return ordersService.transitToReturn(order_id);
    }
}
