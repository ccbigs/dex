package com.dexWare.service;

import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:36
 * @message:
 */
public interface SearchWareService {

    /*模糊搜索栏目框*/
    JsonObject getPromptWare(String ware_name);

    /*全部商品的模糊搜索*/
    JsonObject getSearchWares(String ware_name);

}
