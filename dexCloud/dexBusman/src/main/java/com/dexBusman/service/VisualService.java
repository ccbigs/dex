package com.dexBusman.service;



import dto.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.List;

public interface VisualService {

    /*返回左上角的销售额分析柱状图*/
     VSalesVolume salesVolume(int busman_id) throws ParseException;

    /*返回第二张图环形图的数据*/
    List<VTwoVolume> twoVolume(int busman_id);

     /*返回商品第三张表的数据*/
     VThreeVolume threeData(int busman_id);

     /*返回商品的第五章表的数据*/
     List<VFiveVolume> fiveData(int busman_id) throws ParseException;

     /*返回商品第六张表的数据*/
     List<VSixVolume> sixData(int busman_id);

     /*返回第四章表的数据*/
     List<VFourVolume> fourData(int busman_id);
}
