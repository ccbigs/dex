package com.dex.controller;

import com.dex.service.TabService;
import domain.infoPojo;
import domain.tabPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.JsonObject;
import utils.getTimeNow;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-10-08-时间:15:58
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/tab")
public class TabController {

    @Autowired
    private TabService tabService;

    /*管理员获取全部的tab标签*/
    @RequestMapping("getAllTab")
    JsonObject AdminGetAllTab(){
        JsonObject jsonObject = new JsonObject();
        List<tabPojo> tabPojos = tabService.AdminGetAllTab();

        if (tabPojos.size() > 0){
            jsonObject.setCode(200);
            jsonObject.setObject(tabPojos);
            jsonObject.setSize(tabPojos.size());
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员新建标签*/
    @RequestMapping("AdminBuildNewTab")
    JsonObject AdminBuildNewTab(tabPojo tab){
        JsonObject jsonObject = new JsonObject();

        if ("一级标题".equals(tab.getTabType())){
            tab.setInfoId(-1);
            tab.setTabChain("顶级标题");
        }else {
            tab.setInfoId(0);
        }

        if (tabService.AdminBuildNewTab(tab) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*获取标题对应的文章*/
    @RequestMapping("/getTabInfo")
    JsonObject getTabInfo(int info_id){
        JsonObject jsonObject = new JsonObject();
        infoPojo tabInfo = tabService.getTabInfo(info_id);

        if (tabInfo == null){
            jsonObject.setCode(500);
        }else {
            jsonObject.setCode(200);
            jsonObject.setObject(tabInfo);
            jsonObject.setSize(1);
        }

        return jsonObject;
    }

    /*发表二级标题文章*/
    @RequestMapping("/adminPublicInformationByTab")
    JsonObject adminPublicInformationByTab(infoPojo info){
        info.setPublic_time(getTimeNow.getStaticTimeNowYMDHMS());

        JsonObject jsonObject = new JsonObject();
        int result = tabService.adminPublicInformationByTab(info);

        if (result > 0){
            jsonObject.setCode(200);
            jsonObject.setSize(result);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员更新文章*/
    @RequestMapping("adminUpdateInformationByTab")
    JsonObject adminUpdateInformationByTab(infoPojo info){
        JsonObject jsonObject = new JsonObject();

        if (tabService.adminUpdateInformationByTab(info) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*删除标题*/
    @PostMapping("/DeleteTab")
    JsonObject DeleteTab(tabPojo tab){
        JsonObject jsonObject = new JsonObject();
        if (tabService.DeleteTab(tab) == 200){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }


}
