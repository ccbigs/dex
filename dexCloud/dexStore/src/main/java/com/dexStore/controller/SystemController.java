package com.dexStore.controller;

import com.dexStore.service.SystemService;
import domain.systemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-10-09-时间:22:43
 * @message:
 */
@RequestMapping("/system")
@RestController
@CrossOrigin
public class SystemController {

    @Autowired
    private SystemService systemService;


    /*获取当前启用的系统配置*/
    @RequestMapping("/getEnableSystem")
    JsonObject getEnableSystem(){
        JsonObject jsonObject = new JsonObject();
        systemPojo enableSystem = systemService.getEnableSystem();
        System.out.println(enableSystem);
        if (enableSystem == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(enableSystem);
        }

        return jsonObject;
    }

    /*更新常用的系统配置信息*/
    @RequestMapping("/updateUsuallySystem")
    JsonObject updateUsuallySystem(systemPojo system){
        JsonObject jsonObject = new JsonObject();

        if(systemService.updateUsuallySystem(system) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*获取dex商城电话*/
    @RequestMapping("/getDexPhone")
    JsonObject getDexPhone(){
        JsonObject jsonObject = new JsonObject();
        String dexPhone = systemService.getDexPhone();

        if (dexPhone == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(dexPhone);
        }

        return jsonObject;
    }

    /*获取热搜词语*/
    @PostMapping("/getDexSearch")
    JsonObject getDexSearch(){
        JsonObject jsonObject = new JsonObject();
        String dexSearch = systemService.getDexSearch();

        if (dexSearch == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(dexSearch);
        }

        return jsonObject;
    }


    /*更改全部的配置信息*/
    @PostMapping("/updateAllSystem")
    JsonObject updateAllSystem(systemPojo system){
        JsonObject jsonObject = new JsonObject();

        if (systemService.updateAllSystem(system) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*获取dex商城的logo*/
    @RequestMapping("/getDexLogo")
    JsonObject getDexLogo(){
        JsonObject jsonObject = new JsonObject();
        String dexLogo = systemService.getDexLogo();

        if (dexLogo == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(dexLogo);
        }

        return jsonObject;
    }

}
