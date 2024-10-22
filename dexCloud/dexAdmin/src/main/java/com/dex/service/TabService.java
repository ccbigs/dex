package com.dex.service;

import domain.infoPojo;
import domain.tabPojo;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-10-08-时间:16:00
 * @message:
 */
public interface TabService {

    /*管理员获取全部的标题信息*/
    List<tabPojo> AdminGetAllTab();

    /*管理员新建标签*/
    int AdminBuildNewTab(tabPojo tab);

    /*获取标题对应的文章*/
    infoPojo getTabInfo(int info_id);

    /*管理员发布标题对应的文章*/
    int adminPublicInformationByTab(infoPojo info);

    /*管理员更新文章*/
    int adminUpdateInformationByTab(infoPojo info);

    /*删除标题*/
    int DeleteTab(tabPojo tab);


}
