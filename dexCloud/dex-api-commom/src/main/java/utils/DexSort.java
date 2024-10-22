package utils;

import java.util.*;

/**
 * @author:左泽林
 * @date:日期:2021-09-08-时间:11:26
 * @message:
 */
public class DexSort {


    /*将map从大到小进行排序，排序数据存放在list数组中*/
    public static List MapSort(Map map){
        List<Map.Entry<String,Double>> list = new ArrayList<>(map.entrySet()); //将map的entryset放入list集合
        //对list进行排序，并通过Comparator传入自定义的排序规则
        //对list进行排序，并通过Comparator传入自定义的排序规则
        Collections.sort(list, (o1, o2) -> {
            //重写排序规则，小于0表示升序，大于0表示降序
            return (int) -(o1.getValue()-o2.getValue());
        });

        return list;
    }
}
