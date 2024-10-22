package com.dexStore.controller;

import com.dexStore.service.HomeService;
import domain.tabPojo;
import dto.HOneVolume;
import dto.HTwoVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;
import vo.homeVo;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:16:23
 * @message:
 */
@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping("/getAllHome")
    public homeVo getAllHome(){
        return homeService.getAllHome();
    }

    /*主页第一个面板数据*/
    @RequestMapping("/getOneData")
    public HOneVolume getOneData(){
        return homeService.getOneData();
    }

    /*主页第二个面板数据*/
    @RequestMapping("/getTwoData")
    public HTwoVolume getTwoData(){
        return homeService.getTwoData();
    }

    /*返回底部栏的所有标题信息*/
    @PostMapping("/getAllFootTab")
    public JsonObject getAllFootTab(){
        JsonObject jsonObject = new JsonObject();
        List<tabPojo> allFootTab = homeService.getAllFootTab();

        if (allFootTab.size() >0){
            jsonObject.setCode(200);
            jsonObject.setSize(allFootTab.size());
            jsonObject.setObject(allFootTab);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
