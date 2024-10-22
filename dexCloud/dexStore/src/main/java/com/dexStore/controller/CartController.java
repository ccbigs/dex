package com.dexStore.controller;

import com.dexStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:50
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /*添加商品到*/
    @RequestMapping("/addCartWare")
    JsonObject addCartWare(int ware_id, int user_id, int number,int standard_id){
        System.out.println(ware_id+","+user_id+","+number+","+standard_id);
        return cartService.addCartWare(ware_id, user_id, number,standard_id);
    }

    /*返回用户得购物车,若是购物车中的数量大于库存的数量，则返回购物车的数量为库存的数量*/
    @RequestMapping("/getUserCartWare")
    JsonObject getUserCartWare(int user_id){
        return cartService.getUserCartWare(user_id);
    }


    /*用户点击购物车商品数量，数据库商品数量更新*/
    @PostMapping("/updateCartNumber")
    void userUpdateCartNumber(int cart_id,int cart_number){
        cartService.userUpdateCartNumber(cart_id, cart_number);
    }


    /*用户移除购物车中的商品*/
    @RequestMapping("/removeUserCartWare")
    JsonObject removeUserCartWare(int cart_id,int user_id){
        System.out.println(cart_id+","+user_id);
        return cartService.removeUserCartWare(cart_id, user_id);
    }

}
