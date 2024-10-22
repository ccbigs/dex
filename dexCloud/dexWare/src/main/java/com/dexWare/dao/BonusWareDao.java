package com.dexWare.dao;

import domain.bonusOrderPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-15-时间:15:05
 * @message:
 */
@Repository
public interface BonusWareDao {

    /*时间到了，秒杀结束,关闭bonus的mysql表数据*/
    int closeBonus(int bonus_id);

    /*时间到了，更新商品表的数据信息*/
    int closeWare(warePojo ware);

    /*时间到了，更新商品表中的商品规格信息*/
    int closeStandard(@Param("standards_number") int standards_number,@Param("standard_id") int standard_id);

    /*3.游客购买了商品,生成相应订单*/
    int TouristBuyBonusWare(bonusOrderPojo bonusOrder);

    /*4.更改商品状态，从秒杀状态变成售卖中*/
    @Select("update ware set ware_state='售卖中' where id=#{ware_id}")
    void updateWareStateToSaleing(int ware_id);

    /*5.更改商品状态，从秒杀变为下架，因为该商品的全部规格的剩余量只剩下0*/
    @Select("update ware set ware_state='已下架' where id=#{ware_id}")
    void updateWareStateToDone(int ware_id);
}
