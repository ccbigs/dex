package com.dexUser.dao;


import domain.phimgPojo;

import java.util.List;

/**
 * @author:左定权
 * @date:日期:2021-07-23-时间:21:27
 * @message:图形验证面向数据库的接口
 */

public interface PictureVerificationDao {
    /*随机读取验证的图片*/
    List<phimgPojo> pictureVerification();

}
