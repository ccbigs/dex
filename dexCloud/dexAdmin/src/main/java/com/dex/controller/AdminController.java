package com.dex.controller;

import com.dex.service.AdminService;
import domain.adminPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JsonObject;

import java.text.ParseException;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-25-时间:14:15
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/DexAdmin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /*一级管理员和获取全部的管理员信息*/
    @PostMapping("/GetAllAdmins")
    JsonObject GetAllAdmins(String level) throws ParseException {
        JsonObject jsonObject = new JsonObject();

        if ("一级管理员".equals(level)){
            List<adminPojo> admins = adminService.GetAllAdmins();
            jsonObject.setCode(200);
            jsonObject.setSize(admins.size());
            jsonObject.setObject(admins);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员修改状态信息*/
    @PostMapping("UpdateAdminState")
    JsonObject UpdateAdminState(int admin_id,String state){
        JsonObject jsonObject = new JsonObject();

        if (adminService.UpdateAdminState(admin_id, state) > 0){
            jsonObject.setCode(200);
        }else{
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*管理员修改备注信息*/
    @PostMapping("UpdateRemarks")
    JsonObject UpdateRemarks(int adminId,String remarks){
        JsonObject jsonObject = new JsonObject();

        if (adminService.UpdateRemarks(adminId, remarks) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*一级管理员添加二级管理员*/
    @PostMapping("/addSecondAdmin")
    JsonObject addSecondAdmin(adminPojo admin){
        JsonObject jsonObject = new JsonObject();

        if (adminService.addSecondAdmin(admin) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

    /*一级管理员删除二级管理员*/
    @PostMapping("/deleteSecondAdmin")
    JsonObject deleteSecondAdmin(int adminId){
        JsonObject jsonObject = new JsonObject();

        if (adminService.deleteSecondAdmin(adminId) > 0){
            jsonObject.setCode(200);
        }else {
            jsonObject.setCode(500);
        }

        return jsonObject;
    }

}
