package com.dex.serviceImpl;

import com.dex.dao.UserDao;
import com.dex.service.UserService;
import domain.userPojo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:左泽林
 * @date:日期:2021-08-10-时间:15:38
 * @message:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<userPojo> adminGetAllUser() {
        return userDao.adminGetAllUser();
    }

    /*
     * @author: 左先生
     * @date: 2021-07-21 11:43
     * @description:管理员更新用户的状态
     */
    @Override
    public int adminModifyUserState(int id, String state) {
        userPojo userPojo = new userPojo();

        userPojo.setId(id);
        if ("账号封禁".equals(state)){
            userPojo.setUser_state("封禁");
        }else if ("账号启用".equals(state)){
            userPojo.setUser_state("正常");
        }

        return userDao.adminModifyUserState(userPojo);
    }

    @Override
    public int adminDeleteUser(int id) {
        return userDao.adminDeleteUser(id);
    }


}
