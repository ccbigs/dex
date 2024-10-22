package com.dex.service;

import domain.warePojo;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:52
 * @message:
 */
public interface WareService {
    /*管理员获取全部的商品信息*/
    List<warePojo> adminGetAllWare() throws ParseException;

    /*管理员更改商品状态*/
    int adminModifyWareState(int id,String state);

    /*管理员删除选中多个商品*/
    int adminDeleteWare(int id);
}
