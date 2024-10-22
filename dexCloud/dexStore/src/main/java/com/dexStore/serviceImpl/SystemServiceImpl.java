package com.dexStore.serviceImpl;

import com.dexStore.dao.SystemDao;
import com.dexStore.service.SystemService;
import domain.systemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:左泽林
 * @date:日期:2021-10-09-时间:22:44
 * @message:
 */
@Service
public class SystemServiceImpl implements SystemService{

    @Autowired
    private SystemDao systemDao;

    /*获取当前启用的系统配置*/
    @Override
    public systemPojo getEnableSystem() {
        return systemDao.getEnableSystem();
    }

    /*更新常用的系统配置信息*/
    @Override
    public int updateUsuallySystem(systemPojo system) {
        return systemDao.updateUsuallySystem(system);
    }

    @Override
    public int updateAllSystem(systemPojo system) {
        return systemDao.updateAllSystem(system);
    }

    @Override
    public String getDexPhone() {
        return systemDao.getDexPhone();
    }

    /*获取热搜词语*/
    @Override
    public String getDexSearch() {
        return systemDao.getDexSearch();
    }

    /*获得dex商城的logo*/
    @Override
    public String getDexLogo() {
        return systemDao.getDexLogo();
    }
}
