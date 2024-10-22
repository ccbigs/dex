package com.dex.manager;

import domain.adminPojo;
import dto.AdminPanleTopDTO;
import dto.ESAdminMsgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-11-时间:15:00
 * @message:
 */
@Component
public class adminDataPanleMagImpl implements adminDataPanleMag{

    @Autowired
    private RedisTemplate redisTemplate;


    /*从mysql中拿取面板数据*/
    @Override
    public void PanelDataSynchronization(AdminPanleTopDTO adminPanleTopDTO) {
        /*获取全部的用户信息*/
        redisTemplate.opsForValue().set("AllUser", adminPanleTopDTO.getAllUser());
        redisTemplate.opsForValue().set("AllBusman", adminPanleTopDTO.getAllBusman());
        redisTemplate.opsForValue().set("AllOrder", adminPanleTopDTO.getAllOrder());
        redisTemplate.opsForValue().set("AllComment",adminPanleTopDTO.getAllComment());
        redisTemplate.opsForValue().set("AllSumTurnover",adminPanleTopDTO.getAllSumTurnover());
        redisTemplate.opsForValue().set("AllActuallyTurnover",adminPanleTopDTO.getAllActuallyTurnover());
        redisTemplate.opsForValue().set("LimitQutient",adminPanleTopDTO.getLimitQutient());
        redisTemplate.opsForValue().set("AllQutientTurnover",adminPanleTopDTO.getAllQutientTurnover());
    }

    /*返回面板最上方的所有数据*/
    @Override
    public AdminPanleTopDTO GetAllPanleTop() {
        AdminPanleTopDTO adminPanleTopDTO = new AdminPanleTopDTO();

        adminPanleTopDTO.setAllUser((Integer) redisTemplate.opsForValue().get("AllUser"));
        adminPanleTopDTO.setAllBusman((Integer) redisTemplate.opsForValue().get("AllBusman"));
        adminPanleTopDTO.setAllOrder((Integer) redisTemplate.opsForValue().get("AllOrder"));
        adminPanleTopDTO.setAllComment((Integer) redisTemplate.opsForValue().get("AllComment"));
        adminPanleTopDTO.setAllSumTurnover((BigDecimal) redisTemplate.opsForValue().get("AllSumTurnover"));
        adminPanleTopDTO.setAllActuallyTurnover((BigDecimal) redisTemplate.opsForValue().get("AllActuallyTurnover"));
        adminPanleTopDTO.setLimitQutient((Integer) redisTemplate.opsForValue().get("LimitQutient"));
        adminPanleTopDTO.setAllQutientTurnover((BigDecimal) redisTemplate.opsForValue().get("AllQutientTurnover"));

        return adminPanleTopDTO;
    }


    /*管理员登录，将管理员信息存入redis，以便进行token判断*/
    @Override
    public void saveAdminMessage(adminPojo admin) {
        redisTemplate.opsForValue().set("admin-"+admin.getAccount(),admin);
    }

    @Override
    public boolean AdminExist(String adminName) {
        return redisTemplate.hasKey(adminName);
    }

    @Override
    public Long peopleActiveData(String peopleDateValue) {
        if (redisTemplate.hasKey(peopleDateValue)){
            return redisTemplate.opsForHash().size(peopleDateValue);
        }else {
            return 0L;
        }
    }





    /*=============================ES============================*/

    /*获取全部的节点信息*/
    @Override
    public List<ESAdminMsgDTO> getNodeMessage() {
        return (List<ESAdminMsgDTO>) redisTemplate.opsForList().range("essearchnode", 0,-1);
    }

    /*更新节点的长度,index有三个参数，0代表ware，1代表orders，2代表bonusorder*/
    @Override
    public void UpdateESNodeCount(int index,Long count) {
        if (redisTemplate.hasKey("essearchnode")) {
            ESAdminMsgDTO essearchnode =(ESAdminMsgDTO) redisTemplate.opsForList().index("essearchnode", 0);

            essearchnode.setCount(count);

            redisTemplate.opsForList().set("essearchnode",index,essearchnode);
        }
    }




}
