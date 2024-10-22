package com.dexStore.service;

import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:51
 * @message:
 */
public interface CartService {
    /*添加数量*/
    JsonObject addCartWare(int ware_id, int user_id, int number,int standard_id);

    /*返回购物车*/
    JsonObject getUserCartWare(int user_id);

    /*用户点击购物车商品数量，数据库商品数量更新*/
    void userUpdateCartNumber(int cart_id,int cart_number);

    /*移除商品*/
    JsonObject removeUserCartWare(int ware_id,int user_id);
}
