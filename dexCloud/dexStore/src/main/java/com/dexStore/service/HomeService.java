package com.dexStore.service;

import domain.tabPojo;
import dto.HOneVolume;
import dto.HTwoVolume;
import javafx.scene.control.Tab;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vo.homeVo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:23
 * @message:
 */
public interface HomeService {

    homeVo getAllHome();

    /*主页第一个面板数据*/
    HOneVolume getOneData();


    /*主页第二个面板数据*/
    HTwoVolume getTwoData();


    /*返回底部栏的所有标题信息*/
    List<tabPojo> getAllFootTab();
}
