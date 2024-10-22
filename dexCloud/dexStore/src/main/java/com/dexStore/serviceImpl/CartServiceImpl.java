package com.dexStore.serviceImpl;

import com.dexStore.dao.CartDao;
import com.dexStore.service.CartService;
import domain.cartPojo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:51
 * @message:
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartDao cartDao;

    /*添加购物车*/
    @Override
    public JsonObject addCartWare(int ware_id, int user_id, int number,int standard_id) {
        /*先查询购物车，更具购物车返回的结果进行判断是增加还是修改*/
        JsonObject jsonObject = new JsonObject();
        int result=0;

        if (cartDao.judgeWareIdNumber(user_id , ware_id, standard_id) == 1){
            /*存在，更新即可*/
            result = cartDao.updateCartNumber(user_id,ware_id, standard_id, number);
        }else {
            /*不存在，新增*/
            result = cartDao.addCartWare(ware_id, user_id, number, standard_id);
        }

        if (result>0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*返回用户的购物车数据*/
    @Override
    public JsonObject getUserCartWare(int user_id) {
        System.out.println(user_id);
        List<cartPojo> userCartWares = cartDao.getUserCartWare(user_id);
        for (cartPojo userCartWare : userCartWares) {
            System.out.println(userCartWare);
        }
        JsonObject cartJson = new JsonObject();
        /*判断购物车中是否有东西*/
        if (userCartWares == null){
            cartJson.setCode(500);
        }else {
            /*判断购物车里的数量比商品原有得数量多不多*/
            for (cartPojo userCartWare : userCartWares) {
                /*如果商品得数量为-1，那么表示该商品已经下架了*/
                if (userCartWare.getWare_number() <= 0){
                    userCartWare.setCart_number(0);
                }else {
                    if (userCartWare.getCart_number() > userCartWare.getWare_number()) {
                        userCartWare.setCart_number(userCartWare.getWare_number());
                    }
                }
            }

            cartJson.setCode(200);
            cartJson.setSize(userCartWares.size());
            cartJson.setObject(userCartWares);

        }

        return cartJson;
    }

    /*更新购物车中的商品数量*/
    @Override
    public void userUpdateCartNumber(int cart_id, int cart_number) {
        cartDao.userUpdateCartNumber(cart_id, cart_number);
    }

    /*用户移除购物车中的东西*/
    @Override
    public JsonObject removeUserCartWare(int ware_id, int user_id) {
        JsonObject removeJson = new JsonObject();
        if (cartDao.removeUserCartWare(ware_id, user_id) > 0){
            removeJson.setCode(200);
        }else {
            removeJson.setCode(500);
        }
        return removeJson;
    }
}
