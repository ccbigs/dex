package com.dexStore.serviceImpl;

import com.dexStore.dao.HomeDao;
import com.dexStore.manager.HomeRedisMsg;
import com.dexStore.service.HomeService;
import domain.carouselPojo;
import domain.tabPojo;
import domain.warePojo;
import dto.HOneVolume;
import dto.HTwoVolume;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.DexSort;
import utils.getTimeNow;
import vo.homeVo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:24
 * @message:
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeDao homedao;

    @Autowired
    private HomeRedisMsg homeRedisMsg;

    @Override
    public homeVo getAllHome() {
        homeVo homeVo = new homeVo();

        List<carouselPojo> allCarousel = homedao.getAllCarousel();
        List<warePojo> allHot = homedao.getAllHot();
        /* List<warePojo> allNew = homedao.getAllNew();*/
        List<warePojo> allRecommend = homedao.getAllRecommend();

        homeVo.setCarousel(allCarousel);
        homeVo.setHp_hot(allHot);
        /* homeVo.setHp_new(allNew);*/
        homeVo.setHp_recommend(allRecommend);
        homeVo.setCode(200);
        homeVo.setSize(allCarousel.size()+allHot.size()+allRecommend.size());
        //homeVo.setSize(allCarousel.size()+allHot.size()+allNew.size()+allRecommend.size());

        return homeVo;
    }

    /*主页第一个面板的数据*/
    @Override
    public HOneVolume getOneData() {
        HOneVolume hOneVolume = new HOneVolume();
        Map oneData = homeRedisMsg.getOneData();
        /*用户id，后期可用来排序*/
        String[] userId = {"","","","",""};
        BigDecimal[] value={new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)};
        int index = 5;
        /*排序算法*/
        if (oneData == null){
            return null;
        }else {
            //用迭代器对list中的键值对元素进行遍历
            Iterator<Map.Entry<String, Double>> iter = DexSort.MapSort(oneData).iterator();
            while(iter.hasNext()){
                index--;
                Map.Entry<String, Double> item = iter.next();
                /*先将带货数据存入到value数组,保留两位小数，其他的剔除*/
                value[index] = new BigDecimal(item.getValue()).setScale(2,BigDecimal.ROUND_DOWN);
                /*然后访问数据库，将reids中存储的id替换成数据库返回的昵称*/
                String userNameById = homedao.getUserNameById(Integer.parseInt(item.getKey()));
                if (userNameById == null){
                    userId[index] = "匿名";
                }else {
                    userId[index] = userNameById;
                }

               /* String key = item.getKey();
                Double s= item.getValue();
                System.out.println("键"+key+"值"+value);*/
                if (index == 0){
                    break;
                }
            }
            /*装填数据*/
            hOneVolume.setName(userId);
            hOneVolume.setValue(value);

            return hOneVolume;
        }



    }

    @Override
    public HTwoVolume getTwoData() {
        HTwoVolume hTwoVolume = new HTwoVolume();
        Long[] userActive={0L,0L,0L,0L,0L,0L,0L};
        Long[] busmanActive={0L,0L,0L,0L,0L,0L,0L};
        Long[] touristActive={0L,0L,0L,0L,0L,0L,0L};

        for (int i = 0; i < 7  ; i++) {
            userActive[i] = homeRedisMsg.getUserDateActive("user"+ getTimeNow.getDayAddYMS(i-6));
            busmanActive[i] = homeRedisMsg.getBusmanDateActive("busman"+getTimeNow.getDayAddYMS(i-6));
            touristActive[i] = homeRedisMsg.getTouristDateActive(getTimeNow.getDayAddYMS(i-6));
        }

        hTwoVolume.setUserActive(userActive);
        hTwoVolume.setBusmanActive(busmanActive);
        hTwoVolume.setTouristActive(touristActive);

        return hTwoVolume;
    }


    /*返回底部栏的所有标题信息*/
    @Override
    public List<tabPojo> getAllFootTab() {
        return homedao.getAllFootTab();
    }
}
