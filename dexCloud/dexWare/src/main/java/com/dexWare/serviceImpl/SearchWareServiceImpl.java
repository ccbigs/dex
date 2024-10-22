package com.dexWare.serviceImpl;

import com.dexWare.dao.SearchWareDao;
import com.dexWare.service.SearchWareService;
import domain.warePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:36
 * @message:
 */
@Service
public class SearchWareServiceImpl implements SearchWareService {


    @Autowired
    private SearchWareDao searchWareDao;

    @Override
    public JsonObject getPromptWare(String ware_name) {
        JsonObject wareJson = new JsonObject();
        List<warePojo> searchWare = searchWareDao.getPromptWare(ware_name);

        /*搜索提示信息不能超过五条*/
        if (searchWare.size()>5){
            searchWare=searchWare.subList(0,5);
        }

        wareJson.setCode(200);
        wareJson.setSize(searchWare.size());
        wareJson.setObject(searchWare);

        return wareJson;
    }

    @Override
    public JsonObject getSearchWares(String ware_name) {
        JsonObject waresJson = new JsonObject();
        List<warePojo> searchWares = searchWareDao.getSearchWares(ware_name);

        waresJson.setObject(searchWares);
        waresJson.setSize(searchWares.size());
        waresJson.setCode(200);

        return waresJson;
    }

}
