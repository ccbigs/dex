package com.dexBusman.dao;

import domain.busmanPojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import utils.JsonObject;

/**
 * @author:左泽林
 * @date:日期:2021-10-13-时间:11:37
 * @message:
 */
@Repository
public interface BusmanDao {

    /*更改地址信息*/
    @Update("update busman set bus_local=#{bus_local} where id=#{id}")
    int updateLocal(busmanPojo busman);

    /*商家更改登陆密码*/
    @Update("update busman set bus_password=#{newPass} where id=#{id}")
    int updatePassword(@Param("id") int id, @Param("newPass") String newPass);
}
