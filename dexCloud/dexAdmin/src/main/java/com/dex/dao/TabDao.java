package com.dex.dao;

import domain.infoPojo;
import domain.tabPojo;
import javafx.scene.control.Tab;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-10-08-时间:15:59
 * @message:
 */
@Repository
public interface TabDao {

    /*获取全部的tab标题*/
    List<tabPojo> AdminGetAllTab();

    /*管理员新建标签*/
    int AdminBuildNewTab(tabPojo tab);

    /*获取标题对应的文章*/
    infoPojo getTabInfo(int info_id);

    /*管理员发布标题对应的文章*/
    int adminPublicInformationByTab(infoPojo info);

    /*管理员发布标题对应的文章成功后，更新标题对应的info文章id*/
    int updateInfoId(@Param("info_id")int info_id,@Param("tab_id")int id);

    /*管理员更新文章*/
    int adminUpdateInformationByTab(infoPojo info);

    /*删除二级标题*/
    @Delete("delete from system_tab where id=#{tab_id}")
    int DeleteTabById(int tab_id);

    /*通过一级标题获取它的子标题*/
    List<tabPojo> getSecondTabByTopTab(String TabChain);

}
