package com.dexBusman.controller;

import com.dexBusman.serviceImpl.VisualServiceImpl;
import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/visual")
public class VisualController {
    @Autowired
    private VisualServiceImpl visualService;

    @RequestMapping("/salesVolume")
    VSalesVolume salesVolume(int busman_id) throws ParseException {
        return visualService.salesVolume(busman_id);
    }

    /*返回第二张图环形图的数据*/
    @RequestMapping("/twoData")
    List<VTwoVolume> twoVolume(int busman_id){
        return visualService.twoVolume(busman_id);
    }

    @RequestMapping("/threeData")
    VThreeVolume threeData(int busman_id){
        return visualService.threeData(busman_id);
    }

    @RequestMapping("/fiveData")
    JsonObject fiveData(int busman_id) throws ParseException {
        List<VFiveVolume> vFiveVolumes = visualService.fiveData(busman_id);
        JsonObject jsonObject = new JsonObject();
        if (vFiveVolumes.size()>0){
            jsonObject.setCode(200);
            jsonObject.setSize(vFiveVolumes.size());
            jsonObject.setObject(vFiveVolumes);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    @RequestMapping("/sixData")
    JsonObject sixData(int busman_id) {
        List<VSixVolume> vSixVolumes = visualService.sixData(busman_id);
        JsonObject jsonObject = new JsonObject();

        if (vSixVolumes.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(vSixVolumes.size());
            jsonObject.setObject(vSixVolumes);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*
     * @author: 左先生
     * @date: 2021-07-18 12:25
     * @description:地图数据的view交互层
     */
    @RequestMapping("/fourData")
    JsonObject fourData(int busman_id){
        JsonObject jsonObject = new JsonObject();
        List<VFourVolume> vFourVolumes = visualService.fourData(busman_id);

        jsonObject.setCode(200);
        jsonObject.setObject(vFourVolumes);
        jsonObject.setSize(vFourVolumes.size());

        return jsonObject;
    }
}
