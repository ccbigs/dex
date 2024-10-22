package com.dex.dao;

import domain.adminPojo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-09-25-时间:14:22
 * @message:
 */
@Repository
public interface AdminDao {

    /*一级管理员获取全部的管理员信息*/
    @Select("select * from admin")
    List<adminPojo> GetAllAdmins();

    /*管理员修改状态信息*/
    @Update("update admin set admin_state=#{state} where id=#{admin_id};")
    int UpdateAdminState(@Param("admin_id") int admin_id, @Param("state") String state);

    /*管理员修改备注信息*/
    @Update("update admin set remarks=#{remarks} where id=#{adminId};")
    int UpdateRemarks(@Param("adminId") int adminId,@Param("remarks") String remarks);

    /*管理员添加二级管理员*/
    int addSecondAdmin(adminPojo admin);

    /*一级管理员删除二级管理员*/
    @Delete("delete from admin where id=#{adminId}")
    int deleteSecondAdmin(int adminId);
}
