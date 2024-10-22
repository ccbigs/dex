package com.dexStore.dao;

import domain.cartPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:51
 * @message:
 */
@Repository
public interface CartDao {

    /*返回查询的ware_id是否存在*/
    int judgeWareIdNumber(@Param("user_id")int user_id,@Param("ware_id") int ware_id,@Param("standard_id") int standard_id);

    /*购物车中存在,在原有的基础数量上添加*/
    int updateCartNumber(@Param("user_id") int user_id,@Param("ware_id") int ware_id,@Param("standard_id") int standard_id,@Param("number") int number);

    /*添加新的购物车得商品*/
    int addCartWare(@Param("ware_id") int ware_id,@Param("user_id") int user_id,@Param("number") int number,@Param("standard_id") int standard_id);


    /*用户点击购物车商品数量，数据库商品数量更新*/
    void userUpdateCartNumber(@Param("cart_id") int cart_id,@Param("cart_number") int cart_number);


    /*返回购物车中得所有信息  连表查询*/
    List<cartPojo> getUserCartWare(int user_id);

    /*移除商品信息*/
    int removeUserCartWare(@Param("cart_id") int cart_id,@Param("user_id") int user_id);
}
