package com.dex.manager;

import domain.adminPojo;
import dto.AdminPanleTopDTO;
import dto.ESAdminMsgDTO;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:10:27
 * @message:
 */
public interface adminDataPanleMag {

    /*管理员同步面板数据*/
    void PanelDataSynchronization(AdminPanleTopDTO adminPanleTopDTO);

    AdminPanleTopDTO GetAllPanleTop();

    /*将管理员的信息存入redis*/
    void saveAdminMessage(adminPojo admin);

    /*判断用户是否存在*/
    boolean AdminExist(String adminName);

    /*获取用户或商家活跃数据,参数是用户加时间，时间是七天之内的时间*/
    Long peopleActiveData(String peopleDateValue);

    /*返回管理界面3个节点的信息*/
    List<ESAdminMsgDTO> getNodeMessage();

    /*更新节点ware的长度*/
    void UpdateESNodeCount(int index,Long count);

}
