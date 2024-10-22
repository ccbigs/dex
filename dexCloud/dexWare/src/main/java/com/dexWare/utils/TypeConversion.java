package com.dexWare.utils;

import java.math.BigDecimal;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:15:30
 * @message:
 */
public class TypeConversion {

    public static BigDecimal ObjectToBigDecimal(Object value,int size){
        return BigDecimal.valueOf((Double) value).setScale(size,BigDecimal.ROUND_DOWN);
    }

}
