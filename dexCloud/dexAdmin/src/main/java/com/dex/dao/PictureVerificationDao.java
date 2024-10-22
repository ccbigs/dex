package com.dex.dao;


import domain.phimgPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:左定权
 * @date:日期:2021-07-23-时间:21:27
 * @message:图形验证面向数据库的接口
 */

public interface PictureVerificationDao {
    /*随机读取验证的图片*/
    List<phimgPojo> pictureVerification();

    /*管理员管理验证图片*/
    List<phimgPojo> GetAllpictureVerification();

    /*管理员添加验证的图片*/
    int AddPictureVerification(phimgPojo phimg);

    /*管理员删除验证图片*/
    int adminDetelePicture(int Picture_id);

    /*管理员编辑验证图片信息*/
    int adminEditPictureVerification(phimgPojo phimg);


}
