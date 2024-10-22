package com.dex.serviceImpl;

import com.dex.dao.AdminDao;
import com.dex.service.AdminService;
import domain.adminPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.getTimeNow;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-25-时间:14:22
 * @message:
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public List<adminPojo> GetAllAdmins() throws ParseException {
        List<adminPojo> admins = adminDao.GetAllAdmins();

        for (adminPojo admin : admins) {
            admin.setCreate_time(getTimeNow.dataBasicToYMDHMS(admin.getCreate_time()));
            admin.setLast_time(getTimeNow.dataBasicToYMDHMS(admin.getLast_time()));
        }

        return admins;
    }

    @Override
    public int UpdateAdminState(int admin_id, String state) {
        int flag;
        if ("禁用账号".equals(state)){
            flag = adminDao.UpdateAdminState(admin_id,"禁用");
        }else {
            flag = adminDao.UpdateAdminState(admin_id,"启用");
        }

        return flag;
    }

    @Override
    public int UpdateRemarks(int adminId, String remarks) {

        if ("".equals(remarks)){
            remarks="暂无信息";
        }

        return adminDao.UpdateRemarks(adminId, remarks);
    }

    @Override
    public int addSecondAdmin(adminPojo admin) {

        admin.setCreate_time(getTimeNow.getStaticTimeNowYMDHMS());
        admin.setLast_time(getTimeNow.getStaticTimeNowYMDHMS());
        admin.setAdmin_type("二级管理员");

        return adminDao.addSecondAdmin(admin);
    }

    @Override
    public int deleteSecondAdmin(int adminId) {
        return adminDao.deleteSecondAdmin(adminId);
    }
}
