package com.dexWare.dao;

import domain.warePojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-12-时间:17:36
 * @message:
 */
@Repository
public interface SearchWareDao {
    /*模糊搜索商品下拉框的提示信息*/
    List<warePojo> getPromptWare(String ware_name);

    /*全部商品中的模糊搜索*/
    List<warePojo> getSearchWares(String ware_name);

}
