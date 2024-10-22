package com.dex.serviceImpl;

import com.dex.dao.InformationDao;
import com.dex.dao.TabDao;
import com.dex.service.TabService;
import domain.infoPojo;
import domain.tabPojo;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-10-08-时间:16:00
 * @message:
 */
@Service
public class TabServiceImpl implements TabService
{

    @Autowired
    private TabDao tabDao;

    @Resource
    private InformationDao informationDao;

    /*管理员获取全部的标题信息*/
    @Override
    public List<tabPojo> AdminGetAllTab() {
        return tabDao.AdminGetAllTab();
    }

    /*管理员新建标签*/
    @Override
    public int AdminBuildNewTab(tabPojo tab) {
        return tabDao.AdminBuildNewTab(tab);
    }

    @Override
    public infoPojo getTabInfo(int info_id) {
        return tabDao.getTabInfo(info_id);
    }

    /*管理员发布标题对应的文章*/
    @Transactional
    @Override
    public int adminPublicInformationByTab(infoPojo info) {
        if (tabDao.adminPublicInformationByTab(info) > 0){
            System.out.println(info);
            /*这里的info.getApprove_number()其实是tab的id值*/
            if (tabDao.updateInfoId(info.getId(),info.getApprove_number()) > 0){
                /*这里返回的应该info的id，用来更新前端的数据*/
                return info.getId();
            }else {
                return 500;
            }
        }else {
            return 500;
        }
    }

    /*管理员更新文章*/
    @Override
    public int adminUpdateInformationByTab(infoPojo info) {
        return tabDao.adminUpdateInformationByTab(info);
    }


    /*管理员删除标题*/
    @Transactional
    @Override
    public int DeleteTab(tabPojo tab) {
        int flag;
        if ("一级标题".equals(tab.getTabType())){
            List<tabPojo> secondTabByTopTab = tabDao.getSecondTabByTopTab(tab.getTabName());

            for (tabPojo tabs : secondTabByTopTab) {
                tabDao.DeleteTabById(tabs.getId());
                if (tabs.getInfoId() > 0){
                    informationDao.deleteInformationById(tabs.getInfoId());
                }
            }

            if (tabDao.DeleteTabById(tab.getId()) > 0){
                flag=200;
            }else {
                flag = 500;
            }

        }else {
            if (informationDao.deleteInformationById(tab.getInfoId())>0 && tabDao.DeleteTabById(tab.getId())>0){
                flag=200;
            }else {
                flag=500;
            }
        }
        return flag;
    }
}
