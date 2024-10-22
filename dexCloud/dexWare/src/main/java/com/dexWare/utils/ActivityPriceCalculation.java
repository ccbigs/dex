package com.dexWare.utils;

import domain.warePojo;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-16-时间:10:11
 * @message:
 */
public class ActivityPriceCalculation {

    public static BigDecimal PriceCalculation(warePojo ware,int buy_number){

        if ("未参与活动".equals(ware.getWare_active())){
            return new BigDecimal(ware.getWare_price()*buy_number);
        }else if ("打折".equals(ware.getWare_active())){
            return new BigDecimal(ware.getWare_price()*buy_number*ware.getWare_activeDiscount()*0.1);
        }else {
            /*查看是否满足条件*/
            if (ware.getWare_activeFullPrice().doubleValue() > ware.getWare_price()*buy_number ){
                /*不满足*/
                return new BigDecimal(ware.getWare_price()*buy_number);
            }else {
                /*满足*/
                return new BigDecimal(ware.getWare_price()*buy_number).subtract(ware.getWare_activeFullReduction());
            }
        }
    }

}
