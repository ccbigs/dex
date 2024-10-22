package com.dexUser.dao;

import domain.bonusOrderPojo;
import domain.bonusPojo;
import domain.standardsPojo;
import domain.warePojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import qo.BonusBonusOrderPojo;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-14-时间:11:02
 * @message:
 */
@Repository
public interface BonusDao {

    /*1.用户点击直播带货商品生成链接*/
    int userAttendBonusByWare(bonusPojo bonus);

    /*1.0.1:开启商品状态悲观锁*/
    @Select("select * from ware where id = #{ware_id} for update;")
    warePojo warePessimisticLock(int ware_id);

    /*1.1 关闭直播商品的在售状态*/
    int updateWareState(int ware_id);

    /*1.2 读取直播商品的全部信息*/
    warePojo getBonusWareMessage(int ware_id);

    /*1.3 读取直播商品的bonus信息*/
    bonusPojo getBonusByWare(@Param("ware_id") int ware_id,@Param("bonus_id") int bonus_id);

    /*1.4 读取直播商品的商品规格信息*/
    List<standardsPojo> getBonusByStandards(int ware_id);


    /*用户请求秒杀结果数据，返回直播数据和分红*/
    List<BonusBonusOrderPojo> getUserBonusDate(int user_id);

    /*返回用户请求的秒杀全部收益和实际收益*/
    List<bonusOrderPojo> getUserProfit(int user_id);
}
