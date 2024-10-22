package com.dex.controller;

import com.dex.service.AdminDataPanleService;
import dto.AOneVolume;
import dto.AThreeVolume;
import dto.ATwoVolume;
import dto.AdminPanleTopDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:11:44
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class AdminDataPanleController {

    @Autowired
    private AdminDataPanleService adminDataPanleService;

    @RequestMapping("/GetAllPanleTop")
    JsonObject GetAllPanleTop(){
        AdminPanleTopDTO adminPanleTopDTO = adminDataPanleService.GetAllPanleTop();
        JsonObject jsonObject = new JsonObject();

        jsonObject.setObject(adminPanleTopDTO);

        return jsonObject;
    }


    /*管理员点击同步数据，即redis和MySQL中的数据进行同步*/
    @PostMapping("/PanelDataSynchronization")
    JsonObject PanelDataSynchronization(){
        JsonObject jsonObject = new JsonObject();
        adminDataPanleService.PanelDataSynchronization();

        jsonObject.setCode(200);

        return jsonObject;
    }

    /*第二个面板的数据*/
    @PostMapping("/DexTwoPanleData")
    JsonObject DexTwoPanleData(){
        JsonObject jsonObject = new JsonObject();
        List<ATwoVolume> aTwoVolumes = adminDataPanleService.DexTwoPanleData();

        if (aTwoVolumes.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setObject(aTwoVolumes);
        }else{
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*第一个面板的数据*/
    @PostMapping("/DexOnePanleData")
    JsonObject DexOnePanleData(){
        JsonObject jsonObject = new JsonObject();
        List<AOneVolume> aOneVolumes = adminDataPanleService.DexOnePanleData();

        if (aOneVolumes.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setObject(aOneVolumes);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*第三个面板的数据*/
    @PostMapping("/DexTherePanleData")
    JsonObject DexTherePanleData(){
        JsonObject jsonObject = new JsonObject();
        AThreeVolume aThreeVolume = adminDataPanleService.DexTherePanleData();

        if (aThreeVolume == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(aThreeVolume);
        }

        return jsonObject;
    }

}
