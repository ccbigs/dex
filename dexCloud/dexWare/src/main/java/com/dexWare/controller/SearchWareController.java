package com.dexWare.controller;

import com.dexWare.service.SearchWareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:35
 * @message:
 */
@RestController
@RequestMapping("/wares")
@CrossOrigin
public class SearchWareController {

    @Autowired
    private SearchWareService searchWareService;

    /*返回商品搜索的数据*/
    @RequestMapping("/getPromptWare")
    public JsonObject getPromptWare(String ware_name){
        return searchWareService.getPromptWare(ware_name);
    }

    /*全部商品中的模糊搜索*/
    @RequestMapping("/getSearchWares")
    public JsonObject getSearchWares(String ware_name){
        return searchWareService.getSearchWares(ware_name);
    }

}
