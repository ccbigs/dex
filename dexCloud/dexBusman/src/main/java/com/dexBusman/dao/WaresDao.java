package com.dexBusman.dao;

import domain.ordersPojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import qo.ware_orderPojo;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:19:38
 * @message:
 */
public interface WaresDao {

    /*商家上传商品*/
    int busmanAddWare(warePojo ware);

    /*根据商家的id返回商家的所有商品*/
    List<ware_orderPojo> getWareByBusman(int busman_id);

    /*根据商家的id查询商品的销售总金额*/
    List<ordersPojo> getWareTotalPriceByBusman(int busman_id);

    /*商家修改自己的商品信息*/
    int busmanUpdateWareMessage(warePojo ware);

    /*商家添加库存*/
    int busmanUpdateWareNumber(@Param("id") int id,@Param("number") int number);

    /*商家下架商品*/
    int busmanUnderCarriageWare(int id);

    /*商家上架商品*/
    int busmanGroundWare(int id);

    /*获取指定商家售卖中的商品*/
    List<warePojo> busmanAlreadyGroundWare(int busman_id);

    /*商家删除指定的物品*/
    @Delete("delete from ware where id=#{wareId} and busman_id=#{busmanId}")
    int busmanDeleteWare(@Param("busmanId") int busmanId, @Param("wareId") int wareId);

    /*商家删除物品的同时，也删除商品的规格信息*/
    @Delete("delete from standards where ware_id=#{ware_id}")
    int busmanDeleteWareToStandard(@Param("ware_id") int ware_id);

    /*商家删除物品的同时，也删除商品的用户购物车添加的信息*/
    @Delete("delete from cart where ware_id=#{ware_id}")
    int busmanDeleteWareToCart(@Param("ware_id") int ware_id);

    /*商家删除物品的同时，也删除商品的轮播图信息*/
    @Delete("delete from hp_carousel where ware_id=#{ware_id}")
    int busmanDeleteWareToCarousel(@Param("ware_id") int ware_id);

    /*商家删除物品时，同时删除推荐商品的信息*/
    @Delete("delete from hp_recommend where ware_id=#{ware_id}")
    int busmanDeleteWareToRecommend(@Param("ware_id") int ware_id);


}
