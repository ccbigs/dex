package com.dex.service;

import domain.adminPojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-25-时间:14:22
 * @message:
 */
public interface AdminService {

    /*一级管理员获取全部的管理员信息*/
    List<adminPojo> GetAllAdmins() throws ParseException;

    /*管理员修改状态信息*/
    int UpdateAdminState(int admin_id,String state);

    /*管理员修改备注信息*/
    int UpdateRemarks(int adminId, String remarks);

    /*管理员添加二级管理员*/
    int addSecondAdmin(adminPojo admin);

    /*一级管理员删除二级管理员*/
    int deleteSecondAdmin(int adminId);
}
